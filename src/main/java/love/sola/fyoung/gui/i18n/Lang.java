package love.sola.fyoung.gui.i18n;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * ***********************************************
 * Created by Sola on 2015/11/30.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Lang {

	public static ResourceBundle bundle;
	public static Map<String, MessageFormat> format_cache = new HashMap<>();

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
