package love.sola.fyoung.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import love.sola.fyoung.gui.SystemTrayLauncher;

/**
 * ***********************************************
 * Created by Sola on 2015/12/24.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class LogViewController {

	public static LogViewController INSTANCE;


	public LogViewController() {
		INSTANCE = this;
	}

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
		tipLabel.setText("Cleared");
		guiConsole.setText("");
	}

	@FXML
	public void onClose(MouseEvent evt) {
		if (((Button) evt.getSource()).getId().startsWith("clear")) {
			tipLabel.setText("Cleared");
			guiConsole.setText("");
		} else {
			SystemTrayLauncher.primaryStage.close();
		}
	}

}
