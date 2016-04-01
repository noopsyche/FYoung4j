package love.sola.fyoung.command.impl;

import love.sola.fyoung.Client;
import love.sola.fyoung.command.Command;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.util.NetUtil;

/**
 * ***********************************************
 * Created by Sola on 2016/3/24.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class LoginCommand {

	@Command("login")
	public void login(String command, String[] args) throws Exception {
		logout(command, args);
		if (NetUtil.isInternet()) {
			System.out.println("Logout failed.");
			return;
		}

	}

	@Command("logout")
	public void logout(String command, String[] args) throws Exception {
		boolean tryLogout = true;
		while (NetUtil.isInternet()) {
			System.out.println("Internet Detected");
			if (Client.config.clientIP != null && tryLogout) {
				System.out.println("Trying to logout..");
				Client.logout();
				tryLogout = false;
				continue;
			}
			System.out.println("Logout failed");
			String line;
			while (true) {
				line = Client.input.promptInput("Type your current LAN IP Address to logout (or 'q' to quit):", "172.xxx.xxx.xxx");
				if (line == null) continue;
				if (!line.matches(Config.IP_REGEX)) {
					System.out.println("Invalid LAN IP Address");
					continue;
				}
				Client.config.clientIP = line;
				System.out.println("Client IP has been set to: " + Client.config.clientIP);
				break;
			}
			tryLogout = true;
		}
	}


}
