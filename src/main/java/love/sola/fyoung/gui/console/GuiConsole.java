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

package love.sola.fyoung.gui.console;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import love.sola.fyoung.gui.SystemTrayLauncher;

import java.io.*;
import java.util.Scanner;

import static love.sola.fyoung.config.Lang.lang;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class GuiConsole extends Thread {

	private static boolean initialized = false;
	private PipedInputStream in;
	private PrintStream sysOut;
	private TextArea guiConsole;

	public GuiConsole(TextArea guiConsole) throws IOException {
		if (initialized) {
			throw new IllegalStateException("GUI Console instance can only be initialize once.");
		} else {
			initialized = true;
		}
		this.guiConsole = guiConsole;
		sysOut = System.out;
		PipedOutputStream pout = new PipedOutputStream();
		in = new PipedInputStream(pout);
		System.setOut(new PrintStream(pout));
		Platform.runLater(() -> SystemTrayLauncher.logView.tipLabel.setText(lang("gui.logconsole.initialized")));
		start();
	}

	@Override
	public void run() {
		Scanner cin = new Scanner(in);
		while (cin.hasNext()) {
			String line = cin.nextLine();
			sysOut.println(line);
			Platform.runLater(() -> {
				guiConsole.appendText(line);
				guiConsole.appendText("\n");
			});
		}
	}

}
