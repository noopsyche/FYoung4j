/*
 * This file is part of FYoung4j.
 *
 * FYoung4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FYoung4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FYoung4j.  If not, see <http://www.gnu.org/licenses/>.
 */

package love.sola.fyoung.util;

import love.sola.fyoung.Client;
import love.sola.fyoung.config.Config;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class NetUtilTest {

	@Test
	public void getMAC() throws Exception {
		Client.config = new Config();
		assertNotNull(NetUtil.getMAC());
	}

	@Test
	public void isInternet() throws Exception {
		//We consider surely that you are connected to the Internet when compiling
		assertTrue(NetUtil.isInternet());
	}

}