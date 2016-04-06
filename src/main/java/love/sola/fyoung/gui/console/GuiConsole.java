package love.sola.fyoung.gui.console;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import love.sola.fyoung.gui.SystemTrayLauncher;

import java.io.*;
import java.util.Scanner;

import static love.sola.fyoung.config.Lang.lang;

/**
 * ***********************************************
 * Created by Sola on 2015/12/25.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class GuiConsole extends Thread {

	private static boolean initialized = false;
	PipedInputStream in;
	PrintStream sysOut;
	TextArea guiConsole;

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
