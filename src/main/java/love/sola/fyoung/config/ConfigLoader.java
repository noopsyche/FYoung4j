package love.sola.fyoung.config;

import love.sola.fyoung.util.NetUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class ConfigLoader {

	public static final File CONFIG_FILE = new File("config.yml");


	public static Config loadConfig(ClassLoader clzLoader) {
		Yaml yml = new Yaml();
		if (!CONFIG_FILE.exists()) {
			saveResource(clzLoader, "config.yml", true);
		}
		try {
			FileInputStream fin = new FileInputStream(CONFIG_FILE);
			Config cfg = yml.loadAs(fin, Config.class);
			fin.close();
			if (Config.I.autoFetchIP) {
				NetUtil.autoConfig();
			}
			if (Config.I.mac == null) {
				Config.I.mac = NetUtil.getMAC();
			}
			System.out.println("Successful loaded config.");
			System.out.println("Config = " + cfg);
			return cfg;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Configuration load failed.");
		return null;
	}

	public static void saveResource(ClassLoader clzLoader, String resourcePath, boolean replace) {
		if (resourcePath == null || resourcePath.equals("")) {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}

		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = clzLoader.getResourceAsStream(resourcePath);

		File outFile = new File(resourcePath);
		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

		if (!outDir.exists()) {
			outDir.mkdirs();
		}

		try {
			if (!outFile.exists() || replace) {
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


}
