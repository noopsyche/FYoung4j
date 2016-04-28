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

package love.sola.fyoung;

import joptsimple.OptionParser;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class Launcher {

	public static void main(String[] args) {
		OptionParser parser = new OptionParser() {
			{
				acceptsAll(asList("?", "h", "help"), "Show the help");
				acceptsAll(asList("no-gui"), "Launch application without graphic user interface");
				acceptsAll(asList("c", "config"), "Specific the config file path.")
						.withOptionalArg()
						.ofType(File.class);
			}
		};
		try {
			parser.printHelpOn(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
