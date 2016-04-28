package love.sola.fyoung.util;

import java.text.DecimalFormat;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class FormatUtil {

	public static String readable(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "", "K", "M", "G", "T" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}
