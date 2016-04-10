package love.sola.fyoung;

import com.google.common.eventbus.EventBus;
import lombok.Getter;
import love.sola.fyoung.auth.Challenge;
import love.sola.fyoung.auth.Login;
import love.sola.fyoung.auth.Logout;
import love.sola.fyoung.command.CommandDispatcher;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.config.ConfigLoader;
import love.sola.fyoung.event.LoginStateChangedEvent;
import love.sola.fyoung.event.NetStateChangedEvent;
import love.sola.fyoung.gui.prompt.PromptInputHandler;
import love.sola.fyoung.log.Log4jAdapter;
import love.sola.fyoung.task.ActiveTask;
import love.sola.fyoung.task.MainThread;
import love.sola.fyoung.util.NetUtil;

import java.io.IOException;
import java.util.Timer;

/**
 * ***********************************************
 * Created by Sola on 2016/1/17.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Client {

	public static final EventBus EVENT_BUS = new EventBus("F-Young");
	public static final Timer TIMER = new Timer("F-Young-Schedule");

	public static Runnable firstTimeConfigurator = null;
	public static Runnable applicationInitiator = null;
	public static PromptInputHandler inputRequester = null;

	public static boolean GUI_MODE = false;
	public static ActiveTask activeTask;
	public static Input input;
	public static Config config;
	public static Config config_raw;
	public static MainThread mainThread;
	public static CommandDispatcher commandDispatcher;
	@Getter
	private static NetState netState = NetState.OFFLINE;
	private static boolean loggedIn = false;

	public static void launch() {
		mainThread = new MainThread();
		mainThread.start();
	}

	public static void loadConfig() throws IOException {
		config_raw = ConfigLoader.loadConfig();
		if (config_raw.username == null) {
			firstTimeConfigurator.run();
		}
	}

	public static void initialize() throws IOException, InstantiationException, IllegalAccessException {
		processConfig();
		input = new Input();
		activeTask = new ActiveTask();
		commandDispatcher = new CommandDispatcher();
		applicationInitiator.run();
		TIMER.schedule(activeTask, 0, config.networkCheckInterval * 1000);
	}

	private static void processConfig() throws IOException {
		config = config_raw.clone();
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
	}

	public static void updateNetState(NetState newState) {
		if (newState != netState) {
			netState = newState;
			EVENT_BUS.post(new NetStateChangedEvent(netState, newState));
		}
	}

	public static boolean isLoggedIn() {
		return loggedIn;
	}

	public static void setLoggedIn(boolean state) {
		if (loggedIn != state) {
			loggedIn = state;
			EVENT_BUS.post(new LoginStateChangedEvent(state));
		}
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
