package love.sola.fyoung.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static love.sola.fyoung.gui.i18n.Lang.lang;

/**
 * ***********************************************
 * Created by Sola on 2016/1/5.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class TrayManager {

	public static Font DEFAULT_FONT;
	public static TrayIcon icon = null;

	static {
		DEFAULT_FONT = new Font(null, Font.PLAIN, Toolkit.getDefaultToolkit().getScreenResolution() / 96 * 12);
	}

	public static void startTray() throws IOException {
		if (SystemTray.isSupported()) {
			System.out.println("Registering system tray...");
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			Image image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/offline_16x16.png"));
			// construct a TrayIcon
			TrayIcon trayIcon = new TrayIcon(image, lang("tray.tooltip"), createPopup());
			// set the TrayIcon properties
			trayIcon.setImageAutoSize(true);
			// add the tray image
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("System tray is not supported.");
		}
	}

	private static PopupMenu createPopup() {
		PopupMenu popup = new PopupMenu();
		// create menu item for the default action
		MenuItem defaultItem = new MenuItem("Test \u4e2d\u6587");
		defaultItem.setFont(DEFAULT_FONT);
		popup.add(defaultItem);
		return popup;
	}


}
