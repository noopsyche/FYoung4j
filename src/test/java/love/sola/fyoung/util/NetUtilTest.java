package love.sola.fyoung.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class NetUtilTest {

	@Test
	public void isInternet() throws Exception {
		//We consider surely that you are connected to the Internet when compiling
		assertTrue(NetUtil.isInternet());
	}

}