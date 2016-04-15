package love.sola.fyoung.gui.console;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import love.sola.fyoung.Client;
import love.sola.fyoung.event.LoginStateChangedEvent;
import love.sola.fyoung.gui.SystemTrayLauncher;
import love.sola.fyoung.gui.util.ResizeListener;
import love.sola.fyoung.gui.util.StageUtil;
import love.sola.fyoung.log.OutputFormatter;

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
	public Label statusLabel;
	public Button clearBtn;
	public TextArea guiConsole;
	public Button editCfgBtn;
	public Button closeBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
	}

	public void setup(Stage stage) {
		this.stage = stage;
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(new Scene(root));
		stage.getScene().setFill(Color.TRANSPARENT);
		StageUtil.iconify(stage, bundle.getString("gui.logconsole.title"));
		ResizeListener.addResizeListener(stage);
		try {
			console = new GuiConsole(guiConsole);
		} catch (IOException e) {
			OutputFormatter.logTrace("GUI Console initializing failed.", e);
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

	private void registerListener() {
		Client.EVENT_BUS.register(new Object() {

			@Subscribe
			public void onLoginStateChanged(LoginStateChangedEvent evt) {
				if (evt.isLoggedIn()) {
					Platform.runLater(() -> {
						statusLabel.setText(bundle.getString("gui.logconsole.status.online"));
						statusLabel.setTextFill(Color.GREEN);
					});
				} else {
					Platform.runLater(() -> {
						statusLabel.setText(bundle.getString("gui.logconsole.status.offline"));
						statusLabel.setTextFill(Color.RED);
					});
				}
			}

		});
	}

}
