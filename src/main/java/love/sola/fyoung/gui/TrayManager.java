package love.sola.fyoung.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * ***********************************************
 * Created by Sola on 2016/1/5.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class TrayManager {

	public static Font DEFAULT_FONT;

	static {
		DEFAULT_FONT = new Font(null, Font.PLAIN, Toolkit.getDefaultToolkit().getScreenResolution() / 96 * 12);
	}

	public static void startTray() throws IOException {
		if (SystemTray.isSupported()) {
			System.out.println("Supported");
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();

			// load an image
			Image image = ImageIO.read(ClassLoader.getSystemResourceAsStream("assets/icon/icon.png"));

			// create a action listener to listen for default action executed on the tray icon
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// execute default action of the application
					// ...
				}
			};
			// create a popup menu
			PopupMenu popup = new PopupMenu();
			// create menu item for the default action
			MenuItem defaultItem = new MenuItem("Test \u4e2d\u6587");
			defaultItem.setFont(DEFAULT_FONT);
			defaultItem.addActionListener(listener);
			popup.add(defaultItem);
			/// ... add other items
			// construct a TrayIcon
			TrayIcon trayIcon = new TrayIcon(image, "Tray Demo", popup);
			// set the TrayIcon properties
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(listener);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
				}
			});
			// ...
			// add the tray image
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			// ...
		} else {
			System.out.println("System tray is not supported.");
		}
	}


}
