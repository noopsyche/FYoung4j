package love.sola.fyoung.config;

import lombok.ToString;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@ToString(exclude = "password")
public class Config implements Cloneable {

	public static final String IP_REGEX = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";

	// -------------------------------------------- //
	// COMMON
	// -------------------------------------------- //
	public String username;
	public String password;
	public boolean heartbeatPacket;
	public boolean autoLogin;

	// -------------------------------------------- //
	// ADVANCED
	// -------------------------------------------- //
	public boolean autoFetchIP;
	public boolean debugMode;
	public boolean useLog4j;
	public boolean useJLine;
	public boolean useSpecifiedDNS;
	public String specifiedDNS;
	public int reloginRetryInterval;
	public int networkCheckInterval;

	// -------------------------------------------- //
	// KERNEL
	// -------------------------------------------- //
	public String clientIP;
	public String nasIP;
	public String mac;
	public boolean isWiFi;

	@Override
	public final Config clone() {
		try {
			return (Config) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
