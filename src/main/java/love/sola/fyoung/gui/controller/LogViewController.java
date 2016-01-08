package love.sola.fyoung.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import love.sola.fyoung.gui.SystemTrayLauncher;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ***********************************************
 * Created by Sola on 2015/12/24.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class LogViewController implements Initializable {

	private ResourceBundle bundle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("location = " + location);
		System.out.println("resources = " + resources);
		bundle = resources;
		SystemTrayLauncher.controller = this;
	}

	@FXML
	public BorderPane root;

	@FXML
	public Label tipLabel;

	@FXML
	public Button clearBtn;

	@FXML
	public TextArea guiConsole;

	@FXML
	public Button editCfgBtn;

	@FXML
	public Button closeBtn;

	@FXML
	public void onClear(MouseEvent evt) {
		tipLabel.setText(bundle.getString("gui.logconsole.cleared"));
		guiConsole.setText("");
	}

	@FXML
	public void onClose(MouseEvent evt) {
		SystemTrayLauncher.primaryStage.close();
	}

	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	public void onLayoutPressed(MouseEvent evt) {
		xOffset = evt.getSceneX();
		yOffset = evt.getSceneY();
	}

	@FXML
	public void onLayoutDrag(MouseEvent evt) {
		root.getScene().getWindow().setX(evt.getScreenX() - xOffset);
		root.getScene().getWindow().setY(evt.getScreenY() - yOffset);
	}

}
