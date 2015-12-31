package love.sola.fyoung.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import love.sola.fyoung.NoGuiLauncher;
import love.sola.fyoung.gui.controller.LogViewController;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class SystemTrayLauncher extends Application {

	public static boolean GUI_MODE = false;
	public static Stage primaryStage = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		SystemTrayLauncher.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/guilog.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		initGuiConsole();
		NoGuiLauncher.init();
		debugInput();
	}

	private void initGuiConsole() {
		try {
			PipedOutputStream pout = new PipedOutputStream();
			PipedInputStream pin = new PipedInputStream(pout);
			System.setOut(new PrintStream(pout));
			Platform.runLater(() -> LogViewController.INSTANCE.guiConsole.setText("GUI Consoled Initialized.\n"));
			new GuiConsoleTask(pin).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void debugInput() {
		new Thread(() -> {
			Scanner cin = new Scanner(System.in);
			while (cin.hasNext()) {
				String l = cin.nextLine();
				if (l.equals("Open")) {
					Platform.runLater(() -> primaryStage.show());
					continue;
				}
				System.out.println(l);
			}
		}).start();
	}

	public static void main(String[] args) {
		GUI_MODE = true;
		launch(args);
		Platform.setImplicitExit(false);
	}

}
