package love.sola.fyoung.command;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * ***********************************************
 * Created by Sola on 2016/3/21.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class ReflectionTest {

	@Test
	public void test() throws IOException {
		String pkg = getClass().getPackage().getName() + ".impl";
		ImmutableSet<ClassPath.ClassInfo> info = ClassPath.from(getClass().getClassLoader()).getTopLevelClassesRecursive(pkg);
		assertThat(info, notNullValue());
		assertThat(info.size(), not(equalTo(0)));
	}

}
