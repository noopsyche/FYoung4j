package love.sola.fyoung;

import love.sola.fyoung.config.Config;
import love.sola.fyoung.log.OutputFormatter;
import love.sola.fyoung.task.ActiveTask;
import love.sola.fyoung.util.NetUtil;

import java.io.IOException;
import java.util.Timer;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class NoGuiLauncher {

	private static Timer timer;

	public static void main(String[] args) {
		root:
		while (true) {
			try {
				checkInternet();

				Client.login();

				if (timer != null) {
					timer.cancel();
				}
				timer = new Timer();
				timer.schedule(new ActiveTask(), 60 * 1000, 60 * 1000);

				System.out.println("*****Enter 'q' to logout*****");

				try {
					String line;
					while ((line = Client.input.readLine()) != null) {
						if ("q".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line)) {
							Client.logout();
							break root;
						}
						if ("relogin".equalsIgnoreCase(line)) {
							System.out.println("Relogging in...");
							continue root;
						}
						System.out.println("Unknown command. (Simple command implementation, not finished yet.)");
					}
				} catch (IOException e) {
					OutputFormatter.logTrace("Error occurred while reading command via jline.", e);
				}
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}


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
