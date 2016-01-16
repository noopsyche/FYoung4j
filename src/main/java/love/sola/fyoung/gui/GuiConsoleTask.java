package love.sola.fyoung.gui;

import javafx.application.Platform;

import java.io.*;
import java.util.Scanner;

import static love.sola.fyoung.gui.i18n.Lang.lang;

/**
 * ***********************************************
 * Created by Sola on 2015/12/25.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class GuiConsoleTask extends Thread {

	InputStream in;

	public GuiConsoleTask(InputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		Scanner cin = new Scanner(in);
		while (cin.hasNext()) {
			String line = cin.nextLine();
			Platform.runLater(() -> {
				SystemTrayLauncher.logView.guiConsole.appendText(line);
				SystemTrayLauncher.logView.guiConsole.appendText("\n");
			});
		}
	}

	public static void initGuiConsole() {
		try {
			PipedOutputStream pout = new PipedOutputStream();
			PipedInputStream pin = new PipedInputStream(pout);
			System.setOut(new PrintStream(pout));
			Platform.runLater(() -> SystemTrayLauncher.logView.tipLabel.setText(lang("gui.logconsole.initialized")));
			new GuiConsoleTask(pin).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
