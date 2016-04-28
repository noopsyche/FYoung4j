package love.sola.fyoung.gui.config;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import love.sola.fyoung.gui.util.StageUtil;

import java.util.concurrent.CountDownLatch;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class FirstConfigController extends EditConfigController {

	private CountDownLatch latch;

	@Override
	public void setup(Stage stage, Stage modal) {
		throw new UnsupportedOperationException();
	}

	public void setup(Stage stage, CountDownLatch latch) {
		this.latch = latch;
		this.stage = stage;
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root));
		stage.getScene().setFill(Color.TRANSPARENT);

		((HBox) close.getParent()).getChildren().remove(close);
		title.setText(bundle.getString("gui.config.edit.first"));

		StageUtil.iconify(stage, title.getText());
	}

	@Override
	public void onClose(MouseEvent evt) {
		evt.consume();
	}

	public void onSave(MouseEvent evt) {
		super.onSave(evt);
		latch.countDown();
	}

}
