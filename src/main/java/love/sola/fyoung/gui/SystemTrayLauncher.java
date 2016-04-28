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

package love.sola.fyoung.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import love.sola.fyoung.Client;
import love.sola.fyoung.config.Lang;
import love.sola.fyoung.gui.config.EditConfigController;
import love.sola.fyoung.gui.console.LogViewController;
import love.sola.fyoung.gui.impl.BasicImplements;

import java.io.IOException;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class SystemTrayLauncher extends Application {

	public static Stage logViewStage = null;
	public static Stage configStage = null;
	public static LogViewController logView = null;
	public static EditConfigController configView = null;

	public static void main(String[] args) throws IOException {
		Client.GUI_MODE = true;
		BasicImplements.setupImplements();
		Platform.setImplicitExit(false);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		logViewStage = primaryStage;
		configStage = new Stage();
		loadLogViewStage();
		loadConfigStage();
		Client.launch();
	}

	private void loadLogViewStage() throws IOException {
		FXMLLoader loader = new FXMLLoader(FXMLResource.guilog, Lang.bundle);
		loader.load();
		logView = loader.getController();
		logView.setup(logViewStage);
	}

	private void loadConfigStage() throws IOException {
		FXMLLoader loader = new FXMLLoader(FXMLResource.edit_config, Lang.bundle);
		loader.load();
		configView = loader.getController();
		configView.setup(configStage, logViewStage);
	}

}
