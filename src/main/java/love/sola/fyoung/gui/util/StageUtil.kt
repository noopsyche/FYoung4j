package love.sola.fyoung.gui.util

import javafx.scene.image.Image
import javafx.stage.Stage

/**
 * ***********************************************
 * Created by Sola on 2016/4/6.
 * Don't modify this source without my agreement
 * ***********************************************
 */
object StageUtil {

    @JvmStatic
    @JvmOverloads
    fun iconify(stage: Stage, title: String? = null) {
        stage.icons.addAll(
                Image(this.javaClass.getResourceAsStream("/assets/icon/icon_16x16.png")),
                Image(this.javaClass.getResourceAsStream("/assets/icon/icon_24x24.png")),
                Image(this.javaClass.getResourceAsStream("/assets/icon/icon_32x32.png")),
                Image(this.javaClass.getResourceAsStream("/assets/icon/icon_48x48.png")),
                Image(this.javaClass.getResourceAsStream("/assets/icon/icon_128x128.png"))
        )
        if (title != null) {
            stage.title = title
        }
    }

}
