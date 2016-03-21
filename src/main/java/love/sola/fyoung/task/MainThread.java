package love.sola.fyoung.task;

import love.sola.fyoung.Client;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.log.OutputFormatter;
import love.sola.fyoung.util.NetUtil;

import java.io.IOException;

/**
 * ***********************************************
 * Created by Sola on 2016/3/19.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class MainThread extends Thread {

	public MainThread() {
		super("MainThread");
	}

	@Override
	public void run() {
		try {
			Client.loadConfig();
			Client.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		command:
		while (true) {
			try {
				String line;
				while ((line = Client.input.readLine()) != null) {
					Client.commandDispatcher.dispatch(line);
				}
			} catch (IOException e) {
				OutputFormatter.logTrace("Error occurred while reading command via jline.", e);
			}
		}
//		root:
//		while (true) {
//			try {
//				checkInternet();
//
//				Client.login();
//
//				System.out.println("*****Enter 'q' to logout*****");
//
//
//				System.exit(0);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		System.exit(0);
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
