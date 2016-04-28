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

import love.sola.fyoung.Client;
import love.sola.fyoung.NetState;
import love.sola.fyoung.gui.tray.TrayManager;
import love.sola.fyoung.log.OutputFormatter;
import love.sola.fyoung.util.NetUtil;

import java.io.IOException;

import static love.sola.fyoung.Client.*;
import static love.sola.fyoung.config.Lang.lang;

/**
 * @author Sola {@literal <dev@sola.love>}
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
