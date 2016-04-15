package love.sola.fyoung.task;

import love.sola.fyoung.Client;
import love.sola.fyoung.NetState;
import love.sola.fyoung.gui.tray.TrayManager;
import love.sola.fyoung.log.OutputFormatter;
import love.sola.fyoung.util.NetUtil;

import java.io.IOException;

import static love.sola.fyoung.Client.*;
import static love.sola.fyoung.config.Lang.lang;

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
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (NetUtil.isInternet()) {
			Client.updateNetState(NetState.ONLINE);
			System.out.println("Current Internet Detected. No login required.");
			TrayManager.infoMessage(lang("tray.autologin.alreadyInternet"));
		} else if (config.autoLogin) {
			input.writeToInput("login");
		}
		try {
			while (true) {
				String line;
				while ((line = input.readLine()) != null) {
					commandDispatcher.dispatch(line);
				}
			}
		} catch (IOException e) {
			OutputFormatter.logTrace("Error occurred while reading command.", e);
		}
	}

}
