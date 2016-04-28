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

package love.sola.fyoung.command;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class CommandDispatcherTest {

	@Test
	public void test() throws IllegalAccessException, IOException, InstantiationException, NoSuchFieldException {
		CommandDispatcher dispatcher = new CommandDispatcher();
		Field f = dispatcher.getClass().getDeclaredField("executorMap");
		f.setAccessible(true);
		Map map = (Map) f.get(dispatcher);
		assertThat(map.size(), not(0));
	}

}