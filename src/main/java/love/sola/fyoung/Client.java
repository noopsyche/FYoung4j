package love.sola.fyoung;

import love.sola.fyoung.auth.Challenge;
import love.sola.fyoung.auth.Login;
import love.sola.fyoung.auth.Logout;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.config.ConfigLoader;
import love.sola.fyoung.log.Log4jAdapter;
import love.sola.fyoung.task.ActiveTask;
import love.sola.fyoung.util.NetUtil;

import java.io.IOException;

/**
 * ***********************************************
 * Created by Sola on 2016/1/17.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Client {

	public static boolean GUI_MODE = false;
	public static ActiveTask activeTask;
	public static Input input;
	public static Config config;
	public static Config config_raw;

	public static void initialize() {



	}


	private static void loadConfig() throws IOException {
		config_raw = ConfigLoader.loadConfig();
		if (config_raw == null) {
			throw new NullPointerException("Configuration load failed.");
		}
		config = config_raw.clone();
		initialize();
		if (config.useSpecifiedDNS) {
			NetUtil.resetDNS();
		}
		if (config.useLog4j) {
			Log4jAdapter.loadLog4j();
		}
		if (config.autoFetchIP) {
			NetUtil.autoFetchIP();
		}
		if (config.mac == null) {
			config.mac = NetUtil.getMAC();
		}
		System.out.println("Successful loaded config.");
		System.out.println("Config = " + config);
	}


	public static void login() throws Exception {
		System.out.println("Challenging token...");
		String token = Challenge.post(Challenge.configure(config.username));
		System.out.println("Success challenged token: " + token);
		System.out.println("Logging in...");
		System.out.println(Login.post(Login.configure(config.username, config.password, token)));
	}

	public static void logout() throws Exception {
		System.out.println("Logging out...");
		System.out.println(Logout.post(Logout.configure()));
	}





}
