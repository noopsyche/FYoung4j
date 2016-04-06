package love.sola.fyoung.command;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * ***********************************************
 * Created by Sola on 2016/4/6.
 * Don't modify this source without my agreement
 * ***********************************************
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