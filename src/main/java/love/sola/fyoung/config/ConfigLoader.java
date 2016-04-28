/*
 * This file is part of FYoung4j.
 *
 * FYoung4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FYoung4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FYoung4j.  If not, see <http://www.gnu.org/licenses/>.
 */

package love.sola.fyoung.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

import java.beans.IntrospectionException;
import java.io.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class ConfigLoader {

	public static final File CONFIG_FILE = new File("config.yml");

	protected static Representer repr;
	protected static Yaml yaml;

	static {
		repr = new Representer();
		repr.setPropertyUtils(new PropertyUtils(){
			@Override
			protected Set<Property> createPropertySet(Class<?> type, BeanAccess bAccess) throws IntrospectionException {
				Set<Property> properties = new LinkedHashSet<>();
				Collection<Property> props = getPropertiesMap(type, bAccess).values();
				for (Property property : props) {
					if (property.isReadable() && property.isWritable()) { //Allow read only = false
						properties.add(property);
					}
				}
				return properties;
			}
		});
		yaml = new Yaml(repr);
	}

	public static Config loadConfig() throws IOException {
		if (!CONFIG_FILE.exists()) {
			saveResource("config.yml", true);
		}
		try (FileInputStream fin = new FileInputStream(CONFIG_FILE)) {
			return yaml.loadAs(fin, Config.class);
		}
	}

	public static void saveConfig(Config config) throws IOException {
		try (FileWriter writer = new FileWriter(CONFIG_FILE, false)) {
			writer.write(yaml.dumpAs(config, null, DumperOptions.FlowStyle.BLOCK));
		}
	}

	public static void saveResource(String resourcePath, boolean replace) throws IOException {
		if (resourcePath == null || resourcePath.equals("")) {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}
		File outFile = new File(resourcePath);
		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		if (!outFile.exists() || replace) {
			try (InputStream in = ClassLoader.getSystemResourceAsStream(resourcePath);
			     OutputStream out = new FileOutputStream(outFile)) {
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
			}
		}
	}

}
