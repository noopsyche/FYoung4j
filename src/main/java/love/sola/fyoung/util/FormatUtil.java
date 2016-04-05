package love.sola.fyoung.util;

import java.text.DecimalFormat;

/**
 * ***********************************************
 * Created by Sola on 2016/4/4.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class FormatUtil {

	public static String readable(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "", "K", "M", "G", "T" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}
