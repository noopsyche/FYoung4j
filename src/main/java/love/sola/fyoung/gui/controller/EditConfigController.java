package love.sola.fyoung.gui.controller;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import love.sola.fyoung.Client;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.config.ConfigLoader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ***********************************************
 * Created by Sola on 2016/1/9.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class EditConfigController implements Initializable {

	private ResourceBundle bundle;
	@Getter
	private Stage stage;

	public BorderPane root;
	public Label title;
	public TextField account;
	public PasswordField password;
	public CheckBox heartBeatPacket;

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
	}

	public void onClose(MouseEvent evt) {
		account.setText(Client.config_raw.username);
		password.setText(Client.config_raw.password);
		heartBeatPacket.setSelected(Client.config_raw.heartbeatPacket);
		stage.close();
	}

	public void onSave(MouseEvent evt) {
		Config.I.username = account.getText();
		Config.I.password = password.getText();
		Config.I.heartbeatPacket = heartBeatPacket.isSelected();
		ConfigLoader.saveConfig();
		stage.close();
	}

	public void setFirst() {
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
