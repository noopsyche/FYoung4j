package love.sola.fyoung.timer;

import love.sola.fyoung.auth.Active;
import love.sola.fyoung.config.Config;
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
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Active Failed");
			if (!NetUtil.isInternet()) {
				
			}
		}
	}

}
