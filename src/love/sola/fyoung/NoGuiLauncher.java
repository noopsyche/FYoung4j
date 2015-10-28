package love.sola.fyoung;

import love.sola.fyoung.auth.Challenge;
import love.sola.fyoung.auth.Login;
import love.sola.fyoung.auth.Logout;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.config.ConfigLoader;
import love.sola.fyoung.timer.ActiveTask;

import java.util.Scanner;
import java.util.Timer;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class NoGuiLauncher {

	private static Timer timer = new Timer();

	public static void main(String[] args) {
		ConfigLoader.loadConfig(NoGuiLauncher.class.getClassLoader());
		if (Config.I.useLog4j) {
			love.sola.fyoung.log.LogManager.loadLog4j();
		}
		root:
		while (true) {
			try {
				System.out.println("Challenging token...");
				String token = Challenge.post(Challenge.configure(Config.I.username));
				System.out.println("Success challenged token: " + token);
				System.out.println("Logging in...");
				System.out.println(Login.post(Login.configure(Config.I.username, Config.I.password, token)));
				timer.schedule(new ActiveTask(), 60 * 1000, 60 * 1000);
				System.out.println("*****Enter 'q' to logout*****");
				Scanner cin = new Scanner(System.in);
				String line;
				while (cin.hasNextLine()) {
					line = cin.nextLine();
					if ("q".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line)) {
						System.out.println("Logging out...");
						System.out.println(Logout.post(Logout.configure()));
						break root;
					}
					if ("relogin".equalsIgnoreCase("line")) {
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

}
