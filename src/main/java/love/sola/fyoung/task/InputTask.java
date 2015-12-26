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
	public ConsoleReader reader;
	public Scanner pipe_reader;
	public BufferedReader c_reader;

	public InputTask() {
		try {
			sysIn = System.in;
			sysOut = System.out;
			pipeIn = new PipedInputStream();
			pipeOut = new PipedOutputStream(pipeIn);
			System.setIn(pipeIn);
			writer = new PrintStream(pipeOut);
			if (Config.I.useJline && !SystemTrayLauncher.GUI_MODE) {
				reader = new ConsoleReader(System.in, sysOut);
			} else {
				pipe_reader = new Scanner(sysIn);
				c_reader = new BufferedReader(new InputStreamReader(System.in));
			}
		} catch (IOException e) {
			DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
		}
	}

	@Override
	public void run() {
		if (Config.I.useJline) {
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
					if (!pipe_reader.hasNextLine()) break;
					writer.println(pipe_reader.nextLine());
				} catch (Exception e) {
					DebugLogger.logTrace("Error occurred while warping System.in to pipe.", e);
				}
			}
		}
	}

	public String readLine() throws IOException {
		if (Config.I.useJline) {
			return reader.readLine("> ");
		} else {
			return c_reader.readLine();
		}
	}

}
