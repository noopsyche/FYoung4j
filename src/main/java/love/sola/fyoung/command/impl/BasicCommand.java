package love.sola.fyoung.command.impl;

import javafx.application.Platform;
import love.sola.fyoung.Client;
import love.sola.fyoung.command.Command;

/**
 * ***********************************************
 * Created by Sola on 2016/3/21.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class BasicCommand {

	@Command("quit")
	public void quit(String command, String[] args) {
		if (Client.GUI_MODE) {
			Platform.exit();
		} else {
			System.exit(0);
		}
	}

	@Command("gc")
	public void gc(String command, String[] args) {

	}


}
