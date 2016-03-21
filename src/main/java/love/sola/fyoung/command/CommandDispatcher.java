package love.sola.fyoung.command;

import com.google.common.reflect.ClassPath;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ***********************************************
 * Created by Sola on 2016/3/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class CommandDispatcher {

	private Map<String, CommandExecutor> executorMap = new HashMap<>();

	public CommandDispatcher() throws IOException {
		String pkg = getClass().getPackage().getName() + ".impl";
		ClassPath.from(getClass().getClassLoader()).getTopLevelClassesRecursive(pkg);
	}

	private void registerExecutor(String command, CommandExecutor executor) {
		Validate.notEmpty(command);
		Validate.notNull(executor);
		executorMap.put(command, executor);
	}

	public void dispatch(String line) {
		String[] splited = StringUtils.split(line);
		dispatch(splited[0], ArrayUtils.subarray(splited, 1, splited.length));
	}

	private void dispatch(String command, String[] args) {
		Validate.notNull(command);
		CommandExecutor executor = executorMap.get(command);
		if (executor == null) {
			System.out.println("Unknown command. (Simple command implementation, not finished yet.)");
			return;
		}
		executor.execute(command, args); //ignore return boolean value for now.
	}


}
