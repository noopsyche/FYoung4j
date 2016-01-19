package love.sola.fyoung.task;

import love.sola.fyoung.Client;
import love.sola.fyoung.auth.Active;
import love.sola.fyoung.config.Config;
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
			String result = Active.post(Active.configure(Config.I.username));
			if ("在线".equals(result)) {
				System.out.println("Active Success");
			} else {
				OutputFormatter.logTrace("Active failed with return result :" + result, null);
			}
		} catch (Exception e) {
			System.out.println("Active Failed");
			OutputFormatter.logTrace("Active failed with trace:",e);
			if (!NetUtil.isInternet()) {
				Client.input.writeLine("relogin");
			}
		}
	}



}
