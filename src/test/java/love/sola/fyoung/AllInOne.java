package love.sola.fyoung;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class AllInOne {

	public void testTray() throws IOException {
		int pixelPerInch=java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println("pixelPerInch: "+pixelPerInch);
		/*
			100% (96 DPI)
			125% (120 DPI)
			150% (144 DPI)
			200% (192 DPI)
		 */
		TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) {
			System.out.println("Supported");
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();

			// load an image
			Image image = ImageIO.read(getClass().getResourceAsStream("/assets/icon/offline_16x16.png"));

			// create a action listener to listen for default action executed on the tray icon
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// execute default action of the application
					// ...
				}
			};
			// create a popup menu
			PopupMenu popup = new PopupMenu("Label");
			// create menu item for the default action
			MenuItem defaultItem = new MenuItem("Test \u4e2d\u6587");
			defaultItem.setFont(new Font("Microsoft Yahei UI", Font.PLAIN, Toolkit.getDefaultToolkit().getScreenResolution() / 96 * 12));
			defaultItem.addActionListener(listener);
			popup.add(defaultItem);
			popup.setFont(new Font("Microsoft Yahei UI", Font.PLAIN, Toolkit.getDefaultToolkit().getScreenResolution() / 96 * 12));
			/// ... add other items
			// construct a TrayIcon
			trayIcon = new TrayIcon(image, "Tray Demo", popup);
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
				System.err.println(e);
			}
			// ...
		} else {
			// disable tray option in your application or
			// perform other actions
		}
		// ...
		// some time later
		// the application state has changed - update the image
//		if (trayIcon != null) {
//			trayIcon.setImage(updatedImage);
//		}
		// ...
		Scanner cin = new Scanner(System.in);
		cin.nextLine();
	}

}
