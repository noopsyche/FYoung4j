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

package love.sola.fyoung.gui.tray

import java.awt.Image
import javax.imageio.ImageIO

/**
 * @author Sola
 */
object IconResource {

    @JvmField
    val ICON_ONLINE: Image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/online_16x16.png"))

    @JvmField
    val ICON_OFFLINE: Image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/offline_16x16.png"))

    @JvmField
    val ICON_LAN: Image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/lan_16x16.png"))

}

