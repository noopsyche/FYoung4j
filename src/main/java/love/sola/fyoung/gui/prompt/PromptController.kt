package love.sola.fyoung.gui.prompt

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle
import love.sola.fyoung.config.Lang
import love.sola.fyoung.gui.FXMLResource
import love.sola.fyoung.gui.util.StageUtil
import java.net.URL
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * @author Sola
 */
class PromptController : Initializable {

    private var bundle: ResourceBundle? = null

    var stage: Stage? = null
        private set

    internal lateinit var latch: SolaLatch<String>
    @FXML var root: VBox? = null
    @FXML var tip: Label? = null
    @FXML var input: TextField? = null
    @FXML var confirm: Button? = null

    override fun initialize(location: URL, resources: ResourceBundle) {
        bundle = resources
    }

    internal fun setup(stage: Stage, tip: String, placeHolder: String, latch: SolaLatch<String>) {
        this.latch = latch
        this.stage = stage
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.scene = Scene(root)
        stage.scene.fill = Color.TRANSPARENT
        this.tip!!.text = tip
        this.input!!.promptText = placeHolder
        stage.onCloseRequest = EventHandler { onClose() }
        stage.onHidden = EventHandler { onClose() }
        StageUtil.iconify(stage, tip)
    }

    fun onEnter(keyEvent: KeyEvent) {
        if (keyEvent.code == KeyCode.ENTER) {
            keyEvent.consume()
            onClick()
        }
    }

    fun onClick() {
        stage!!.close()
    }

    fun onClose() {
        latch.value = input!!.text
        latch.latch.countDown()
    }
}

interface PromptInputHandler {
    fun requestInput(tip: String, placeHolder: String): String?
}

internal data class SolaLatch<T>(val latch: CountDownLatch, var value: T? = null)

object GUIPromptInputHandler {

    @JvmStatic
    fun requestInput(tip: String, placeHolder: String): String? {
        var latch = SolaLatch<String>(latch = CountDownLatch(1))
        Platform.runLater {
            var stage = Stage()
            var loader = FXMLLoader(FXMLResource.tip_dialog, Lang.bundle)
            loader.load<Any>()
            var controller = loader.getController<PromptController>()
            controller.setup(stage, tip, placeHolder, latch)
            stage.toFront()
            stage.showAndWait()
        }
        latch.latch.await()
        return latch.value
    }

}