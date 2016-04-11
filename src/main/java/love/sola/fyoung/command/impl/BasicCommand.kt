package love.sola.fyoung.command.impl

import javafx.application.Platform
import love.sola.fyoung.Client
import love.sola.fyoung.command.Command
import love.sola.fyoung.gui.SystemTrayLauncher
import love.sola.fyoung.util.FormatUtil

/**
 * ***********************************************
 * Created by Sola on 2016/3/21.
 * Don't modify this source without my agreement
 * ***********************************************
 */
class BasicCommand {

    @Command("quit")
    fun quit(command: String, args: Array<String>) {
        if (Client.GUI_MODE) {
            Platform.exit()
            System.exit(0)
        } else {
            System.exit(0)
        }
    }

    @Command("gc")
    fun gc(command: String, args: Array<String>) {
        val rt = Runtime.getRuntime()
        val f = FormatUtil::readable;
        println("Free Mem: ${f(rt.freeMemory())}")
        println("Max Mem: ${f(rt.maxMemory())}")
        println("Total Mem: ${f(rt.totalMemory())}")
    }

    @Command("console")
    fun console(command: String, args: Array<String>) {
        if (Client.GUI_MODE) {
            Platform.runLater {
                SystemTrayLauncher.logViewStage.show()
            }
        }
    }

    @Command("config")
    fun config(command: String, args: Array<String>) {
        if (Client.GUI_MODE) {
            Platform.runLater {
                SystemTrayLauncher.configStage.show()
            }
        }
    }

}
