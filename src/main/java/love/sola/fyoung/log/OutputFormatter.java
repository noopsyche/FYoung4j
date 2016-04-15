package love.sola.fyoung.log;

import love.sola.fyoung.Client;
import org.apache.logging.log4j.Level;

/**
 * ***********************************************
 * Created by Sola on 2015/11/12.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class OutputFormatter { //TODO Rename

	public static void logTrace(String msg, Throwable e) {
		System.err.println(msg);
		logTrace(e);
	}

	public static void logTrace(Throwable e) {
		if (!Client.config.debugMode) return;
		System.err.println("Cause by: " + e.toString());
		if (Client.config.useLog4j) {
			System.err.println("See log files for more info.");
			Log4jAdapter.getLogger().catching(Level.DEBUG, e);
		} else {
			e.printStackTrace();
		}
	}

}
