package love.sola.fyoung.config;

import lombok.ToString;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@ToString(exclude = "password")
public class Config {

	public static final String IP_REGEX = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";

	public static Config I;

	public String username;
	public String password;
	public boolean heartbeatPacket;
	public boolean routerMode;
	public boolean autoFetchIP;
	public String clientIP;
	public String nasIP = "61.142.108.88";
	public String mac;
	public boolean debugMode;
	public boolean useLog4j;
	public boolean useJline;

	public Config() {
		I = this;
	}

}
