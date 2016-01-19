package love.sola.fyoung.gui.controller;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import love.sola.fyoung.gui.GuiConsole;
import love.sola.fyoung.gui.util.ResizeListener;
import love.sola.fyoung.gui.SystemTrayLauncher;

import java.io.IOException;
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
	@Getter
	public Stage stage;
	public GuiConsole console;

	public BorderPane root;
	public Label tipLabel;
	public Button clearBtn;
	public TextArea guiConsole;
	public Button editCfgBtn;
	public Button closeBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		SystemTrayLauncher.logView = this;
	}

	public void setup(Stage stage) {
		this.stage = stage;
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(new Scene(root));
		stage.getScene().setFill(Color.TRANSPARENT);
		stage.getIcons().addAll(
				new Image(getClass().getResourceAsStream("/assets/icon/icon_16x16.png")),
				new Image(getClass().getResourceAsStream("/assets/icon/icon_24x24.png")),
				new Image(getClass().getResourceAsStream("/assets/icon/icon_32x32.png")),
				new Image(getClass().getResourceAsStream("/assets/icon/icon_48x48.png"))
		);
		ResizeListener.addResizeListener(stage);
		try {
			console = new GuiConsole(guiConsole);
		} catch (IOException e) {
			//TODO Handle exception
		}
	}

	public void onClear(MouseEvent evt) {
		tipLabel.setText(bundle.getString("gui.logconsole.cleared"));
		guiConsole.setText("");
	}

	public void onClose(MouseEvent evt) {
		SystemTrayLauncher.logViewStage.close();
	}

	public void onEditConfig(MouseEvent evt) {
		SystemTrayLauncher.configStage.show();
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
