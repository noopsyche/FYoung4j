package love.sola.fyoung.config;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class Lang {

	public static ResourceBundle bundle;
	private static Map<String, MessageFormat> format_cache = new HashMap<>();

	static {
		bundle = ResourceBundle.getBundle("assets.lang.lang");
	}

	public static String lang(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException | ClassCastException e) {
			return "!!" + key + "!!";
		}
	}

	public static String format(String key, Object... args) {
		MessageFormat cache = format_cache.get(key);
		if (cache != null) {
			return cache.format(args);
		} else {
			cache = new MessageFormat(lang(key));
			format_cache.put(key, cache);
			return cache.format(args);
		}
	}

}
