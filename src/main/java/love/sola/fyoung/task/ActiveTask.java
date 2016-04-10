package love.sola.fyoung.task;

import com.google.common.eventbus.Subscribe;
import love.sola.fyoung.Client;
import love.sola.fyoung.NetState;
import love.sola.fyoung.auth.Active;
import love.sola.fyoung.event.NetStateChangedEvent;
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
		if (Client.isLoggedIn()) {
			if (Client.config.heartbeatPacket) {
				try {
					String result = Active.post(Active.configure(Client.config.username));
					if ("在线".equals(result)) {
						System.out.println("Active Success");
					} else {
						System.err.println("Active failed with return result :" + result);
					}
				} catch (Exception e) {
					OutputFormatter.logTrace("Active Failed", e);
				}
			}
		}
		Client.updateNetState(NetUtil.isInternet() ? NetState.ONLINE : NetState.OFFLINE);
	}

	@Subscribe
	public void relogin(NetStateChangedEvent evt) {
		if (evt.getNow() == NetState.OFFLINE && Client.isLoggedIn()) {
			Client.input.writeToInput("relogin");
		}
	}


}
