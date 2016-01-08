package love.sola.fyoung;

import love.sola.fyoung.auth.Challenge;
import love.sola.fyoung.auth.Login;
import love.sola.fyoung.auth.Logout;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.log.DebugLogger;
import love.sola.fyoung.task.ActiveTask;
import love.sola.fyoung.task.InputTask;
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
	public static InputTask input;

	public static void init() {
		input = new InputTask();
		input.start();
	}

	public static void main(String[] args) {
		init();
		root:
		while (true) {
			try {
				checkInternet();

				checkPortal();

				login();

				if (timer != null) {
					timer.cancel();
				}
				timer = new Timer();
				timer.schedule(new ActiveTask(), 60 * 1000, 60 * 1000);

				System.out.println("*****Enter 'q' to logout*****");

				try {
					String line;
					while ((line = input.readLine()) != null) {
						if ("q".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line)) {
							logout();
							break root;
						}
						if ("relogin".equalsIgnoreCase(line)) {
							System.out.println("Relogging in...");
							continue root;
						}
						System.out.println("Unknown command. (Simple command implementation, not finished yet.)");
					}
				} catch (IOException e) {
					DebugLogger.logTrace("Error occurred while reading command via jline.", e);
				}
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	public static void login() throws Exception {
		System.out.println("Challenging token...");
		String token = Challenge.post(Challenge.configure(Config.I.username));
		System.out.println("Success challenged token: " + token);
		System.out.println("Logging in...");
		System.out.println(Login.post(Login.configure(Config.I.username, Config.I.password, token)));
	}

	public static void logout() throws Exception {
		System.out.println("Logging out...");
		System.out.println(Logout.post(Logout.configure()));
	}

	public static void checkPortal() {
		String portal = NetUtil.getPortal();
		if (portal != null && Config.I.autoFetchIP) {
			NetUtil.autoFetchIP(portal);
		}
	}

	public static void checkInternet() throws Exception {
		boolean tryLogout = true;
		while (NetUtil.isInternet()) {
			System.out.println("Internet Detected");
			if (Config.I.clientIP != null && tryLogout) {
				System.out.println("Trying to logout..");
				logout();
				tryLogout = false;
				continue;
			}
			System.out.println("Logout failed");
			String line;
			while (true) {
				System.out.println("Type your current LAN IP Address to logout (or 'q' to quit):");
				line = input.readLine();
				if (line == null) continue;
				if ("q".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line)) {
					System.exit(0);
					return;
				}
				if (!line.matches(Config.IP_REGEX)) {
					System.out.println("Invalid LAN IP Address");
					continue;
				}
				Config.I.clientIP = line;
				System.out.println("Client IP has been set to: " + Config.I.clientIP);
				break;
			}
			tryLogout = true;
		}
	}



}
