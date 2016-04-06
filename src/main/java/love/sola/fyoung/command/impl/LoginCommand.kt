package love.sola.fyoung.command.impl

import love.sola.fyoung.Client
import love.sola.fyoung.command.Command
import love.sola.fyoung.config.Config
import love.sola.fyoung.config.Lang.lang
import love.sola.fyoung.util.NetUtil

/**
 * ***********************************************
 * Created by Sola on 2016/3/24.
 * Don't modify this source without my agreement
 * ***********************************************
 */
class LoginCommand {

    @Command("login")
    @Throws(Exception::class)
    fun login(command: String, args: Array<String>) {
        logout(command, args)
        if (NetUtil.isInternet()) {
            println("Logout failed.")
        } else {
            Client.login()
        }
    }

    @Command("relogin")
    @Throws(Exception::class)
    fun relogin(command: String, args: Array<String>) {
        if (NetUtil.isInternet()) return
        Client.login()
    }

    @Command("logout")
    @Throws(Exception::class)
    fun logout(command: String, args: Array<String>) {
        var tryLogout = true
        while (NetUtil.isInternet()) {
            println("Internet Detected")
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
    }


}
