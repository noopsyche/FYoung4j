package love.sola.fyoung.task;

import love.sola.fyoung.NoGuiLauncher;

import java.util.Scanner;

/**
 * ***********************************************
 * Created by Sola on 2015/11/1.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class InputTask implements Runnable {

	@Override
	public void run() {
		Scanner cin = new Scanner(System.in);
		while (cin.hasNextLine()) {
			NoGuiLauncher.input.add(cin.nextLine());
		}
	}

}
