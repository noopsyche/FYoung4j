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
