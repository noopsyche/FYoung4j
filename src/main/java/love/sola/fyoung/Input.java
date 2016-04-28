package love.sola.fyoung;

import jline.console.ConsoleReader;
import love.sola.fyoung.log.OutputFormatter;

import java.io.*;
import java.util.Scanner;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class Input extends Thread {

	private static boolean initialized = false;
	private PipedInputStream pipeIn;
	private PipedOutputStream pipeOut;
	private InputStream sysIn;
	private OutputStream sysOut;
	private PrintStream writer;
	private ConsoleReader jLineReader;
	private Scanner sysInReader;
	private BufferedReader pipeInReader;
	private boolean useJLine;

	public Input() throws IOException {
		if (initialized) {
			throw new IllegalStateException("Input instance can only be initialize once.");
		} else {
			initialized = true;
		}
		sysIn = System.in;
		sysOut = System.out;
		pipeIn = new PipedInputStream();
		pipeOut = new PipedOutputStream(pipeIn);
		System.setIn(pipeIn);
		writer = new PrintStream(pipeOut);
		useJLine = Client.config.useJLine && !Client.GUI_MODE;
		if (useJLine) {
			jLineReader = new ConsoleReader(System.in, sysOut);
		} else {
			sysInReader = new Scanner(sysIn);
			pipeInReader = new BufferedReader(new InputStreamReader(pipeIn));
		}

		start();
	}

	public String readLine() throws IOException {
		if (useJLine) {
			return jLineReader.readLine("> ");
		} else {
			return pipeInReader.readLine();
		}
	}

	public String promptInput(String tip, String placeHolder) throws IOException {
		if (Client.GUI_MODE) {
			return Client.inputRequester.requestInput(tip, placeHolder);
		} else {
			System.out.println(tip);
			return readLine();
		}
	}

	public void writeToInput(String line) {
		writer.println(line);
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
					OutputFormatter.logTrace("Error occurred while warping System.in to pipe.", e);
				}
			}
		} else {
			while (true) {
				try {
					if (!sysInReader.hasNextLine()) break;
					writer.println(sysInReader.nextLine());
				} catch (Exception e) {
					OutputFormatter.logTrace("Error occurred while warping System.in to pipe.", e);
				}
			}
		}
	}

}
