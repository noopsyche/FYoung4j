package love.sola.fyoung.task;

import love.sola.fyoung.log.DebugLogger;

import java.io.*;
import java.util.Scanner;

/**
 * ***********************************************
 * Created by Sola on 2015/11/1.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class InputTask extends Thread {

	PipedInputStream pipeIn;
	PipedOutputStream pipeOut;
	InputStream sysIn;
	PrintStream writer;
	Scanner cin;

	public InputTask() {
		try {
			sysIn = System.in;
			pipeIn = new PipedInputStream();
			pipeOut = new PipedOutputStream(pipeIn);
			System.setIn(pipeIn);
			cin = new Scanner(sysIn);
			writer = new PrintStream(pipeOut);
		} catch (IOException e) {
			DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
		}
	}

	@Override
	public void run() {
		while (cin.hasNextLine()) {
			try {
				writer.println(cin.nextLine());
			} catch (Exception e) {
				DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
			}
		}
	}

}
