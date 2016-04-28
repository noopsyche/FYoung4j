package love.sola.fyoung.log;

import love.sola.fyoung.Client;
import org.apache.logging.log4j.Level;

/**
 * @author Sola {@literal <dev@sola.love>}
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
