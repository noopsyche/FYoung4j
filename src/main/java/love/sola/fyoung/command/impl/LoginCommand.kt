package love.sola.fyoung.command.impl

import love.sola.fyoung.Client
import love.sola.fyoung.NetState
import love.sola.fyoung.command.Command
import love.sola.fyoung.config.Config
import love.sola.fyoung.config.Lang.format
import love.sola.fyoung.config.Lang.lang
import love.sola.fyoung.gui.tray.TrayManager
import love.sola.fyoung.log.OutputFormatter
import love.sola.fyoung.util.NetUtil
import kotlin.concurrent.timerTask

/**
 * @author Sola
 */
class LoginCommand {

    @Command("login")
    @Throws(Exception::class)
    fun login(command: String, args: Array<String>) {
        logout(command, args)
        if (NetUtil.isInternet()) {
            println("Logout failed.")
            TrayManager.errorMessage(lang("tray.login.error.logout_failed"))
        } else {
            try {
                val rs = Client.login()
                if (NetUtil.isInternet()) {
                    println("Login success.")
                    TrayManager.infoMessage(lang("tray.login.success"))
                    Client.setLoggedIn(true)
                    Client.updateNetState(NetState.ONLINE)
                } else {
                    println("Login failed.")
                    if (rs != null) {
                        TrayManager.errorMessage(format("tray.login.error.result", rs))
                    } else {
                        TrayManager.errorMessage(lang("tray.login.error.unknown"))
                    }
                }
            } catch(e: Exception) {
                OutputFormatter.logTrace("An error occurred while logging in.", e)
                TrayManager.errorMessage(lang("tray.login.error.exception"))
            }
        }
    }

    @Command("relogin")
    @Throws(Exception::class)
    fun relogin(command: String, args: Array<String>) {
        if (NetUtil.isInternet()) return
        try {
            Client.login()
        } catch(e: Exception) {
            OutputFormatter.logTrace("An error occurred while re-logging in.", e)
        }
        if (NetUtil.isInternet()) {
            println("Re-login success")
            if (args.size > 0 && args[0].equals("retry", true)) {
                TrayManager.errorMessage(format("tray.relogin.success"))
            }
            Client.updateNetState(NetState.ONLINE)
        } else {
            println("Re-login failed.")
            TrayManager.errorMessage(format("tray.relogin.error.exception.retry", Client.config.reloginRetryInterval))
            Client.TIMER.schedule(timerTask {
                if (Client.isLoggedIn()) Client.input.writeToInput("relogin retry")
            }, Client.config.reloginRetryInterval * 1000L)
        }
    }

    @Command("logout")
    @Throws(Exception::class)
    fun logout(command: String, args: Array<String>) {
        var tryLogout = true
        while (NetUtil.isInternet()) {
            println("Internet Detected")

            if (Client.config.nasIP == null) {
                println("NAS IP not configured, manually logout is not available.")
                return
            }

            if (Client.config.clientIP != null && tryLogout) {
                println("Trying to logout..")
                Client.logout()
                tryLogout = false
                continue
            }

            println("Logout failed")
            var line: String?
            while (true) {
                line = Client.input.promptInput(lang("prompt.internet.detected"), "172.xxx.xxx.xxx")
                if (line == null) continue
                if (line.equals("q", true) || line.equals("quit", true)) {
                    println("User interrupted.")
                    return
                }
                if (!line.matches(Config.IP_REGEX.toRegex())) {
                    println("Invalid LAN IP Address")
                    continue
                }
                Client.config.clientIP = line
                println("Client IP has been set to: " + Client.config.clientIP)
                break
            }
            tryLogout = true
        }
        Client.setLoggedIn(false)
        Client.updateNetState(NetState.OFFLINE)
    }


}
