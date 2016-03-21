package love.sola.fyoung.command;

/**
 * ***********************************************
 * Created by Sola on 2016/3/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public interface CommandExecutor {

	boolean execute(String command, String[] args);

}
