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

package love.sola.fyoung.gui.tray;

import com.google.common.eventbus.Subscribe;
import love.sola.fyoung.Client;
import love.sola.fyoung.event.LoginStateChangedEvent;
import love.sola.fyoung.event.NetStateChangedEvent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static love.sola.fyoung.config.Lang.lang;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class TrayManager {

	private static Font DEFAULT_FONT;
	private static TrayIcon trayIcon = null;

	static {
		DEFAULT_FONT = new Font(null, Font.PLAIN, Toolkit.getDefaultToolkit().getScreenResolution() / 96 * 12);
	}

	public static void startTray() {
		if (SystemTray.isSupported()) {
			System.out.println("Registering system tray...");
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			// construct a TrayIcon
			trayIcon = new TrayIcon(IconResource.ICON_OFFLINE, lang("tray.tooltip"), createPopup());
			// set the TrayIcon properties
			trayIcon.setImageAutoSize(true);
			// add the tray image
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			registerStateListener();
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2 && Client.input != null) {
						Client.input.writeToInput("console");
					}
				}
			});
		} else {
			System.out.println("System tray is not supported.");
		}
	}

	private static PopupMenu createPopup() {
		PopupMenu popup = new PopupMenu();
		popup.add(createItem("tray.login", "login"));
		popup.add(createItem("tray.logout", "logout"));
		popup.addSeparator();
		popup.add(createItem("tray.console", "console"));
		popup.add(createItem("tray.config", "config"));
		popup.addSeparator();
		popup.add(createItem("tray.quit", "quit"));
		popup.addActionListener(e -> {
			if (Client.input != null) {
				Client.input.writeToInput(e.getActionCommand());
			}
		});
		popup.getItem(1).setEnabled(false); //disable logout menu
		return popup;
	}

	private static MenuItem createItem(String label, String command) {
		MenuItem item = new MenuItem(lang(label));
		item.setFont(DEFAULT_FONT);
		item.setActionCommand(command);
		return item;
	}

	public static void errorMessage(String text) {
		displayMessage(text, TrayIcon.MessageType.ERROR);
	}

	public static void warningMessage(String text) {
		displayMessage(text, TrayIcon.MessageType.WARNING);
	}

	public static void infoMessage(String text) {
		displayMessage(text, TrayIcon.MessageType.INFO);
	}

	public static void message(String text) {
		displayMessage(text, TrayIcon.MessageType.NONE);
	}

	private static void displayMessage(String text, TrayIcon.MessageType type) {
		if (trayIcon != null) {
			trayIcon.displayMessage(lang("tray.caption"), text, type);
		}
	}

	private static void registerStateListener() {
		Client.EVENT_BUS.register(new Object() {
			@Subscribe
			public void onNetStateChange(NetStateChangedEvent evt) {
				Image icon;
				switch (evt.getNow()) {
					case ONLINE:
						icon = IconResource.ICON_ONLINE;
						break;
					case OFFLINE:
						icon = IconResource.ICON_OFFLINE;
						break;
					default:
						return;
				}
				trayIcon.setImage(icon);
//				String key = evt.getNow().name().toLowerCase();
//				trayIcon.displayMessage(
//						lang("tray.state." + key + ".title"),
//						lang("tray.state." + key + ".msg"),
//						TrayIcon.MessageType.INFO);
			}

			@Subscribe
			public void onLoginStateChange(LoginStateChangedEvent evt) {
				trayIcon.getPopupMenu().getItem(0).setEnabled(!evt.isLoggedIn());
				trayIcon.getPopupMenu().getItem(1).setEnabled(evt.isLoggedIn());
			}

		});
	}

}
