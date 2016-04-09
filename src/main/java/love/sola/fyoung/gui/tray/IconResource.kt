package love.sola.fyoung.gui.tray

import java.awt.Image
import javax.imageio.ImageIO

/**
 * ***********************************************
 * Created by Sola on 2016/4/8.
 * Don't modify this source without my agreement
 * ***********************************************
 */
object IconResource {

    @JvmField
    val ICON_ONLINE: Image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/online_16x16.png"))

    @JvmField
    val ICON_OFFLINE: Image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/offline_16x16.png"))

    @JvmField
    val ICON_LAN: Image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/lan_16x16.png"))

}

