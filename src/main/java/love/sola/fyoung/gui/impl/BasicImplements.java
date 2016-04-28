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
 * @author Sola {@literal <dev@sola.love>}
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
