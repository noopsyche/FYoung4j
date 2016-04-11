package love.sola.fyoung.gui.impl;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import love.sola.fyoung.Client;
import love.sola.fyoung.config.Lang;
import love.sola.fyoung.gui.FXMLResource;
import love.sola.fyoung.gui.config.FirstConfigController;
import love.sola.fyoung.gui.prompt.GUIPromptInputHandler;
import love.sola.fyoung.gui.tray.TrayManager;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static love.sola.fyoung.gui.SystemTrayLauncher.logViewStage;

/**
 * ***********************************************
 * Created by Sola on 2016/3/21.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class BasicImplements {

	public static void setupImplements() {
		Client.firstTimeConfigurator = BasicImplements::firstTimeConfig;
		Client.applicationInitiator = BasicImplements::applicationInitiate;
		Client.inputRequester = GUIPromptInputHandler::requestInput;
	}

	private static void firstTimeConfig() {
		CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(() -> {
			try {
				Stage fccStage = new Stage();
				FXMLLoader loader = new FXMLLoader(FXMLResource.edit_config, Lang.bundle);
				loader.setControllerFactory(param -> new FirstConfigController());
				loader.load();
				FirstConfigController fcc = loader.getController();
				fcc.setup(fccStage, latch);
				fccStage.show();
				fccStage.toFront();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		try {
			latch.await();
			Platform.runLater(() -> logViewStage.show());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void applicationInitiate() {
		Platform.runLater(() -> logViewStage.show());
		TrayManager.startTray();
	}

}
