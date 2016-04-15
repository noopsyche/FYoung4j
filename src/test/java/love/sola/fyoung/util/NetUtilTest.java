package love.sola.fyoung.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * ***********************************************
 * Created by Sola on 2016/4/4.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class NetUtilTest {

	@Test
	public void isInternet() throws Exception {
		//We consider surely that you are connected to the Internet when compiling
		assertTrue(NetUtil.isInternet());
	}

}