package love.sola.fyoung;

import love.sola.fyoung.auth.Challenge;
import love.sola.fyoung.auth.Login;
import love.sola.fyoung.auth.Logout;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.config.ConfigLoader;
import love.sola.fyoung.task.ActiveTask;
import love.sola.fyoung.task.InputTask;
import love.sola.fyoung.util.NetUtil;

import java.util.Scanner;
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

	public static void main(String[] args) {
		ConfigLoader.loadConfig(NoGuiLauncher.class.getClassLoader());
		if (Config.I.useLog4j) {
			love.sola.fyoung.log.LogManager.loadLog4j();
		}

		input = new InputTask();
		input.start();

		root:
		while (true) {
			try {
				if (NetUtil.isInternet()) {
					System.out.println("Internet Detected");
					logout();
				}

				checkPortal();

				login();

				if (timer != null) {
					timer.cancel();
				}
				timer = new Timer();
				timer.schedule(new ActiveTask(), 60 * 1000, 60 * 1000);

				System.out.println("*****Enter 'q' to logout*****");

				Scanner cin = new Scanner(System.in);
				String line;
				while ((line = cin.nextLine()) != null) {
					if ("q".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line)) {
						logout();
						break root;
					}
					if ("relogin".equalsIgnoreCase(line)) {
						System.out.println("Relogging in...");
						continue root;
					}
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
			NetUtil.autoConfig(portal);
		}
	}

}
