package love.sola.fyoung.task;

import love.sola.fyoung.Client;
import love.sola.fyoung.log.OutputFormatter;

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
		} catch (IOException | InstantiationException | IllegalAccessException e) {
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

}
