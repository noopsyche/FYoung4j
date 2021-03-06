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

package love.sola.fyoung.gui.config;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import lombok.Getter;
import love.sola.fyoung.Client;
import love.sola.fyoung.config.ConfigLoader;
import love.sola.fyoung.gui.util.StageUtil;
import love.sola.fyoung.log.OutputFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class EditConfigController implements Initializable {

	protected ResourceBundle bundle;
	@Getter
	protected Stage stage;

	public BorderPane root;
	public Label title;
	public Button close;
	public Button save;

	public TextField account;
	public PasswordField password;
	public CheckBox heartBeatPacket;
	public CheckBox autoLogin;
	public CheckBox isWiFi;
	public CheckBox debugMode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
	}

	public void setup(Stage stage, Stage modal) {
		this.stage = stage;
		stage.initOwner(modal);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(new Scene(root));
		stage.getScene().setFill(Color.TRANSPARENT);

		stage.setOnShowing(this::onShow);

		StageUtil.iconify(stage, title.getText());
	}

	private void onShow(WindowEvent evt) {
		account.setText(Client.config_raw.username);
		password.setText(Client.config_raw.password);
		heartBeatPacket.setSelected(Client.config_raw.heartbeatPacket);
		autoLogin.setSelected(Client.config.autoLogin);
		isWiFi.setSelected(Client.config.isWiFi);
		debugMode.setSelected(Client.config.debugMode);
	}

	public void onClose(MouseEvent evt) {
		stage.close();
	}

	public void onSave(MouseEvent evt) {
		Client.config_raw.username = account.getText();
		Client.config_raw.password = password.getText();
		Client.config_raw.heartbeatPacket = heartBeatPacket.isSelected();
		Client.config_raw.autoLogin = autoLogin.isSelected();
		Client.config_raw.isWiFi = isWiFi.isSelected();
		Client.config_raw.debugMode = debugMode.isSelected();
		try {
			ConfigLoader.saveConfig(Client.config_raw);
		} catch (IOException e) {
			OutputFormatter.logTrace("Config file save failed", e);
		}
		stage.close();
	}

	private double xOffset = 0;
	private double yOffset = 0;

	public void onLayoutPressed(MouseEvent evt) {
		xOffset = evt.getSceneX();
		yOffset = evt.getSceneY();
	}

	public void onLayoutDrag(MouseEvent evt) {
		root.getScene().getWindow().setX(evt.getScreenX() - xOffset);
		root.getScene().getWindow().setY(evt.getScreenY() - yOffset);
	}

}
