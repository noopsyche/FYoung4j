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

package love.sola.fyoung.gui.util

import javafx.scene.image.Image
import javafx.stage.Stage

/**
 * @author Sola
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
