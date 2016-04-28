package love.sola.fyoung.command;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.AllArgsConstructor;
import love.sola.fyoung.log.OutputFormatter;
import love.sola.fyoung.util.collection.CaseInsensitiveMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class CommandDispatcher {

	private Map<String, ExecutorInfo> executorMap = new CaseInsensitiveMap<>();

	public CommandDispatcher() throws IOException, IllegalAccessException, InstantiationException {
		String pkg = getClass().getPackage().getName() + ".impl";
		ImmutableSet<ClassPath.ClassInfo> infoSet = ClassPath.from(getClass().getClassLoader()).getTopLevelClassesRecursive(pkg);
		for (ClassPath.ClassInfo info : infoSet) {
			Class<?> clz = info.load();
			if (!Modifier.isPublic(clz.getModifiers())) continue; //skip non-public
			Object instance = null;
			for (Method method : clz.getDeclaredMethods()) {
				if (!Modifier.isPublic(method.getModifiers())) continue; //skip non-public
				if (!isExecutable(method)) continue; //skip not executable
				if (instance == null) instance = clz.newInstance();
				Command annotation = method.getAnnotation(Command.class);
				registerExecutor(annotation.value(), instance, method);
			}
		}
	}

	private void registerExecutor(String command, Object obj, Method executor) {
		Validate.notEmpty(command);
		Validate.notNull(obj);
		Validate.notNull(executor);
		executorMap.put(command, new ExecutorInfo(command, executor, obj));
	}

	public void dispatch(String line) {
		String[] splited = StringUtils.split(line);
		dispatch(splited[0], ArrayUtils.subarray(splited, 1, splited.length));
	}

	private void dispatch(String command, String[] args) {
		Validate.notNull(command);
		ExecutorInfo info = executorMap.get(command);
		if (info == null) {
			System.out.println("Unknown command. (Simple command implementation, not finished yet.)");
			return;
		}
		try {
			info.execute(command, args); //ignore return boolean value for now.
		} catch (InvocationTargetException | IllegalAccessException e) {
			OutputFormatter.logTrace("An error occurred while executing command: " + command + "  args:" + Arrays.toString(args), e);
		}
	}

	private static boolean isExecutable(Method method) {
		return method.isAnnotationPresent(Command.class)
				&& method.getParameters().length == 2
				&& method.getParameters()[0].getType() == String.class
				&& method.getParameters()[1].getType() == String[].class;
	}

	@AllArgsConstructor
	private static class ExecutorInfo {
		String command;
		Method executor;
		Object obj;

		private void execute(String command, String[] args) throws InvocationTargetException, IllegalAccessException {
			executor.invoke(obj, command, args);
		}
	}

}
