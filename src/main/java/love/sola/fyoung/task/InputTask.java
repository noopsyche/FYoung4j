package love.sola.fyoung.task;

import jline.console.ConsoleReader;
import love.sola.fyoung.log.DebugLogger;

import java.io.*;

/**
 * ***********************************************
 * Created by Sola on 2015/11/1.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class InputTask extends Thread {

	public PipedInputStream pipeIn;
	public PipedOutputStream pipeOut;
	public InputStream sysIn;
	public OutputStream sysOut;
	public PrintStream writer;
	public ConsoleReader reader;

	public InputTask() {
		try {
			sysIn = System.in;
			sysOut = System.out;
			pipeIn = new PipedInputStream();
			pipeOut = new PipedOutputStream(pipeIn);
			System.setIn(pipeIn);
			writer = new PrintStream(pipeOut);
			reader = new ConsoleReader(System.in, sysOut);
		} catch (IOException e) {
			DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
		}
	}

	@Override
	public void run() {
		byte[] buf = new byte[1024];
		int i;
		try {
			while ((i = sysIn.available()) != 0) {
				i = sysIn.read(buf, 0, i);
				pipeOut.write(buf, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readLine() throws IOException {
		return reader.readLine("> ");
	}

}
