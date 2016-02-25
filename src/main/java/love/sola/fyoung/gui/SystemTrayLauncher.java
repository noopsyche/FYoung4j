package love.sola.fyoung.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import love.sola.fyoung.Client;
import love.sola.fyoung.config.Lang;
import love.sola.fyoung.gui.config.EditConfigController;
import love.sola.fyoung.gui.console.LogViewController;

import java.io.IOException;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class SystemTrayLauncher extends Application {

	public static Stage logViewStage = null;
	public static Stage configStage = null;
	public static LogViewController logView = null;
	public static EditConfigController configView = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		logViewStage = primaryStage;
		configStage = new Stage();
		loadLogViewStage();
		logViewStage.show();
		loadConfigStage();
	}

	private void loadLogViewStage() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/fxml/guilog.fxml"), Lang.bundle);
		loader.load();
		logView = loader.getController();
		logView.setup(logViewStage);
	}

	private void loadConfigStage() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/fxml/edit_config.fxml"), Lang.bundle);
		loader.load();
		configView = loader.getController();
		configView.setup(configStage, logViewStage);
	}

	public static void main(String[] args) {
		Platform.setImplicitExit(false);
		launch(args);
		debugInput();
	}

	private static void debugInput() {
		new Thread(() -> {
			while (true) {
				try {
					String l = Client.input.readLine();
					if (l.equals("Open")) {
						Platform.runLater(() -> logViewStage.show());
						continue;
					}
					System.out.println(l);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
