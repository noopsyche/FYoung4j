package love.sola.fyoung.task;

import jline.console.ConsoleReader;
import love.sola.fyoung.config.Config;
import love.sola.fyoung.gui.SystemTrayLauncher;
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

	public PipedInputStream pipeIn;
	public PipedOutputStream pipeOut;
	public InputStream sysIn;
	public OutputStream sysOut;
	public PrintStream writer;
	public ConsoleReader jLineReader;
	public Scanner sysInReader;
	public BufferedReader pipeInReader;
	public boolean useJLine;

	public InputTask() {
		useJLine = Config.I.useJLine && !SystemTrayLauncher.GUI_MODE;
		try {
			sysIn = System.in;
			sysOut = System.out;
			pipeIn = new PipedInputStream();
			pipeOut = new PipedOutputStream(pipeIn);
			System.setIn(pipeIn);
			writer = new PrintStream(pipeOut);
			if (useJLine) {
				jLineReader = new ConsoleReader(System.in, sysOut);
			} else {
				sysInReader = new Scanner(sysIn);
				pipeInReader = new BufferedReader(new InputStreamReader(pipeIn));
			}
		} catch (IOException e) {
			DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
		}
	}

	@Override
	public void run() {
		if (useJLine) {
			byte[] buf = new byte[1024];
			int i;
			while (true) {
				try {
					if ((i = sysIn.available()) == 0) break;
					i = sysIn.read(buf, 0, i);
					pipeOut.write(buf, 0, i);
				} catch (IOException e) {
					DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
				}
			}
		} else {
			while (true) {
				try {
					if (!sysInReader.hasNextLine()) break;
					writer.println(sysInReader.nextLine());
				} catch (Exception e) {
					DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
				}
			}
		}
	}

	public String readLine() throws IOException {
		if (useJLine) {
			return jLineReader.readLine("> ");
		} else {
			return pipeInReader.readLine();
		}
	}

}
