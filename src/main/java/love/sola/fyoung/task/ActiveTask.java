package love.sola.fyoung.task;

import love.sola.fyoung.Client;
import love.sola.fyoung.auth.Active;
import love.sola.fyoung.log.OutputFormatter;
import love.sola.fyoung.util.NetUtil;

import java.util.TimerTask;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class ActiveTask extends TimerTask {

	public ActiveTask() {
		System.out.println("ActiveTask initialized");
	}

	@Override
	public void run() {
		try {
			String result = Active.post(Active.configure(Client.config.username));
			if ("在线".equals(result)) {
				System.out.println("Active Success");
			} else {
				System.err.println("Active failed with return result :" + result);
			}
		} catch (Exception e) {
			System.err.println("Active Failed");
			OutputFormatter.logTrace(e);
			if (!NetUtil.isInternet()) {
				Client.input.writeToInput("relogin");
			}
		}
	}



}
