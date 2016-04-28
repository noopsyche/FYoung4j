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
