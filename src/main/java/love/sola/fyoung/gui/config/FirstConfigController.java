package love.sola.fyoung.gui.config;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import love.sola.fyoung.Client;
import love.sola.fyoung.config.ConfigLoader;
import love.sola.fyoung.log.OutputFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class FirstConfigController implements Initializable {

	private ResourceBundle bundle;
	@Getter
	private Stage stage;
	private CountDownLatch latch;

	public BorderPane root;
	public Label title;
	public TextField account;
	public PasswordField password;
	public CheckBox heartBeatPacket;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
	}

	public void setup(Stage stage, CountDownLatch latch) {
		this.latch = latch;
		this.stage = stage;
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(new Scene(root));
		stage.getScene().setFill(Color.TRANSPARENT);
	}

	public void onSave(MouseEvent evt) throws IOException {
		Client.config_raw.username = account.getText();
		Client.config_raw.password = password.getText();
		Client.config_raw.heartbeatPacket = heartBeatPacket.isSelected();
		try {
			ConfigLoader.saveConfig(Client.config_raw);
		} catch (IOException e) {
			OutputFormatter.logTrace("Config file save failed", e);
		}
		stage.close();
		latch.countDown();
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
