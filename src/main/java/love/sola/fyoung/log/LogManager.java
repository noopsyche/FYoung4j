package love.sola.fyoung.log;

import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class LogManager {

	@Getter
	private static Logger logger;

	public static void loadLog4j() {
		logger = org.apache.logging.log4j.LogManager.getRootLogger();
		System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
		System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.ERROR), true));
	}

}
