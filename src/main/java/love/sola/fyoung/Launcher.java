package love.sola.fyoung;

import joptsimple.OptionParser;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

/**
 * ***********************************************
 * Created by Sola on 2015/12/30.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Launcher {

	public static void main(String[] args) {
		OptionParser parser = new OptionParser() {
			{
				acceptsAll(asList("?", "h", "help"), "Show the help");
				acceptsAll(asList("no-gui"), "Launch application without graphic user interface");
				acceptsAll(asList("c", "config"), "Specific the config file path.")
						.withOptionalArg()
						.ofType(File.class);
			}
		};
		try {
			parser.printHelpOn(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
