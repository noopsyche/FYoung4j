package love.sola.fyoung.task;

import love.sola.fyoung.NoGuiLauncher;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

/**
 * ***********************************************
 * Created by Sola on 2015/11/1.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class InputTask implements Runnable {

	PipedInputStream pipeIn;
	PipedOutputStream pipeOut;
	InputStream sysIn;


	public InputTask() {
		try {
			sysIn = System.in;
			pipeIn = new PipedInputStream();
			pipeOut = new PipedOutputStream(pipeIn);
			System.setIn(pipeIn);
		} catch (IOException e) { }
	}

	@Override
	public void run() {
		Scanner cin = new Scanner(sysIn);
		while (cin.hasNextLine()) {
			NoGuiLauncher.input.add(cin.nextLine());
		}
	}

}
