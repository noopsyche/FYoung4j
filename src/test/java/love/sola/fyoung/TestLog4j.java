package love.sola.fyoung;

import love.sola.fyoung.config.Config;
import love.sola.fyoung.log.Log4jAdapter;
import org.apache.logging.log4j.Level;
import org.junit.Test;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class TestLog4j {

	@Test
	public void test() {
		Client.config = new Config();
		Client.config.useLog4j = true;
		Log4jAdapter.loadLog4j();
		System.out.println("Test stdout");
		System.err.println("Test stderr");
		Log4jAdapter.getLogger().log(Level.DEBUG, "Test trace");
	}

}
