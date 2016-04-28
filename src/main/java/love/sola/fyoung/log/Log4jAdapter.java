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

package love.sola.fyoung.log;

import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class Log4jAdapter {

	@Getter
	private static Logger logger;

	public static void loadLog4j() {
		logger = org.apache.logging.log4j.LogManager.getRootLogger();
		System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
		System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.ERROR), true));
	}

}
