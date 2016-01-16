package love.sola.fyoung.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import love.sola.fyoung.NoGuiLauncher;
import love.sola.fyoung.gui.controller.EditConfigController;
import love.sola.fyoung.gui.controller.LogViewController;
import love.sola.fyoung.gui.i18n.Lang;

import java.io.IOException;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class SystemTrayLauncher extends Application {

	public static boolean GUI_MODE = false;
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

		GuiConsoleTask.initGuiConsole();
		NoGuiLauncher.init();
		debugInput();
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

	private void debugInput() {
		new Thread(() -> {
			while (true) {
				try {
					String l = NoGuiLauncher.input.readLine();
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


	public static void main(String[] args) {
		GUI_MODE = true;
		launch(args);
		Platform.setImplicitExit(false);
	}

}
