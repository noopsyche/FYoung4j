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
