/*
 * This file is part of FYoung4j.
 *
 * FYoung4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FYoung4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FYoung4j.  If not, see <http://www.gnu.org/licenses/>.
 */

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
 * @author Sola {@literal <dev@sola.love>}
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
