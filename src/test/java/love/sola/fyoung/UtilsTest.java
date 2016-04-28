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

package love.sola.fyoung;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class UtilsTest {

	@Test
	public void testArrayCopy1() {
		int[] a = new int[]{1};
		int[] b = ArrayUtils.subarray(a, 1, a.length);
		assertThat(b, notNullValue());
		assertThat(b.length, is(0));
	}

	@Test
	public void testArrayCopy2() {
		int[] a = new int[]{1, 2, 3, 4, 5};
		int[] b = ArrayUtils.subarray(a, 1, a.length);
		assertThat(b, notNullValue());
		assertThat(b.length, is(4));
		assertArrayEquals(b, new int[]{2, 3, 4, 5});
	}

}
