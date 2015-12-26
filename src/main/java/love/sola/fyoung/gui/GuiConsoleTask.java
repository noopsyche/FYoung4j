package love.sola.fyoung.gui;

import javafx.application.Platform;
import love.sola.fyoung.gui.controller.LogViewController;

import java.io.InputStream;
import java.util.Scanner;

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
				LogViewController.INSTANCE.guiConsole.appendText(line);
				LogViewController.INSTANCE.guiConsole.appendText("\n");
			});
		}
	}

}
