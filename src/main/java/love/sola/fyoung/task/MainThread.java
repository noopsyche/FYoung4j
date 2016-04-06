package love.sola.fyoung.task;

import love.sola.fyoung.Client;
import love.sola.fyoung.log.OutputFormatter;

import java.io.IOException;

import static love.sola.fyoung.Client.*;

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
		if (config.autoLogin) {
			input.writeToInput("login");
		}
		command:
		while (true) {
			try {
				String line;
				while ((line = input.readLine()) != null) {
					commandDispatcher.dispatch(line);
				}
			} catch (IOException e) {
				OutputFormatter.logTrace("Error occurred while reading command via jline.", e);
			}
		}
	}

}
