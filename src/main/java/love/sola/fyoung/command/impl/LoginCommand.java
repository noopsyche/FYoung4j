package love.sola.fyoung.command.impl;

import love.sola.fyoung.Client;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.util.NetUtil;

/**
 * ***********************************************
 * Created by Sola on 2016/3/24.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class LoginCommand {


	public static void checkInternet() throws Exception {
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
				System.out.println("Type your current LAN IP Address to logout (or 'q' to quit):");
				line = Client.input.readLine();
				if (line == null) continue;
				if ("q".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line)) {
					System.exit(0);
					return;
				}
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
