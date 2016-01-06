package love.sola.fyoung.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import love.sola.fyoung.NoGuiLauncher;
import love.sola.fyoung.gui.controller.LogViewController;

import java.util.ResourceBundle;
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
	public static LogViewController controller = null;
	public static ResourceBundle bundle = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		SystemTrayLauncher.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/assets/fxml/guilog.fxml"), bundle);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(new Scene(root));
		primaryStage.getScene().setFill(Color.TRANSPARENT);
		primaryStage.getIcons().addAll(
				new Image(getClass().getResourceAsStream("/assets/icon/icon_16x16.png")),
				new Image(getClass().getResourceAsStream("/assets/icon/icon_24x24.png")),
				new Image(getClass().getResourceAsStream("/assets/icon/icon_32x32.png")),
				new Image(getClass().getResourceAsStream("/assets/icon/icon_48x48.png"))
		);
		primaryStage.show();
		GuiConsoleTask.initGuiConsole();
		NoGuiLauncher.init();
		debugInput();
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
		bundle = ResourceBundle.getBundle("assets.lang.lang");
		launch(args);
		Platform.setImplicitExit(false);
	}

}
