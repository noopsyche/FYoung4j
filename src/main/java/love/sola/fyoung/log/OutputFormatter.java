package love.sola.fyoung.log;

import love.sola.fyoung.config.Config;

/**
 * ***********************************************
 * Created by Sola on 2015/11/12.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class OutputFormatter {

	public static void logTrace(String message, Exception e) {
		if (message != null) System.err.println(message);
		if (Config.I.debugMode) {
			if (e != null) e.printStackTrace();
		}
	}

}
