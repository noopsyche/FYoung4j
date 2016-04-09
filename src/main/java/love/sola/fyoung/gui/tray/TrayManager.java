package love.sola.fyoung.gui.tray;

import love.sola.fyoung.Client;

import java.awt.*;
import java.io.IOException;

import static love.sola.fyoung.config.Lang.lang;

/**
 * ***********************************************
 * Created by Sola on 2016/1/5.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class TrayManager {

	/* TODO:
	    Change tray-icon when net state changed.
	    Tray message as a GUI tip.
	    Disable popup-menu item properly
	  */

	private static Font DEFAULT_FONT;
	private static TrayIcon icon = null;

	static {
		DEFAULT_FONT = new Font(null, Font.PLAIN, Toolkit.getDefaultToolkit().getScreenResolution() / 96 * 12);
	}

	public static void startTray() throws IOException {
		if (SystemTray.isSupported()) {
			System.out.println("Registering system tray...");
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			// construct a TrayIcon
			icon = new TrayIcon(IconResource.ICON_OFFLINE, lang("tray.tooltip"), createPopup());
			// set the TrayIcon properties
			icon.setImageAutoSize(true);
			// add the tray image
			try {
				tray.add(icon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
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
		return popup;
	}

	private static MenuItem createItem(String label, String command) {
		MenuItem item = new MenuItem(lang(label));
		item.setFont(DEFAULT_FONT);
		item.setActionCommand(command);
		return item;
	}

	public static void errorMessage(String caption, String text) {
		displayMessage(caption, text, TrayIcon.MessageType.ERROR);
	}

	public static void warningMessage(String caption, String text) {
		displayMessage(caption, text, TrayIcon.MessageType.WARNING);
	}

	public static void infoMessage(String caption, String text) {
		displayMessage(caption, text, TrayIcon.MessageType.INFO);
	}

	public static void message(String caption, String text) {
		displayMessage(caption, text, TrayIcon.MessageType.NONE);
	}

	private static void displayMessage(String caption, String text, TrayIcon.MessageType type) {
		if (icon != null) {
			icon.displayMessage(caption, text, type);
		}
	}

}
