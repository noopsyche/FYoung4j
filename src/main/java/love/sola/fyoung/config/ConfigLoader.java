package love.sola.fyoung.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

import java.beans.IntrospectionException;
import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class ConfigLoader {

	public static final File CONFIG_FILE = new File("config.yml");

	public static Config loadConfig() {
		Yaml yml = new Yaml();
		if (!CONFIG_FILE.exists()) {
			saveResource("config.yml", true);
		}
		try (FileInputStream fin = new FileInputStream(CONFIG_FILE)) {
			return yml.loadAs(fin, Config.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void saveConfig() {
		try (FileWriter writer = new FileWriter(CONFIG_FILE, false)) {
			Representer repr = new Representer();
			repr.setPropertyUtils(new UnsortedPropertyUtils());
			writer.write(new Yaml(repr).dumpAs(Config.I, null, DumperOptions.FlowStyle.BLOCK));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveResource(String resourcePath, boolean replace) {
		if (resourcePath == null || resourcePath.equals("")) {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}

		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = ClassLoader.getSystemResourceAsStream(resourcePath);

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

	private static class UnsortedPropertyUtils extends PropertyUtils {
		@Override
		protected Set<Property> createPropertySet(Class<? extends Object> type, BeanAccess bAccess) throws IntrospectionException {
			return new LinkedHashSet<>(getPropertiesMap(type, BeanAccess.FIELD).values());
		}
	}

}
