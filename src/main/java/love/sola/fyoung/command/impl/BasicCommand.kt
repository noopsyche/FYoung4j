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

package love.sola.fyoung.command.impl

import javafx.application.Platform
import love.sola.fyoung.Client
import love.sola.fyoung.command.Command
import love.sola.fyoung.gui.SystemTrayLauncher
import love.sola.fyoung.util.FormatUtil

/**
 * @author Sola
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
