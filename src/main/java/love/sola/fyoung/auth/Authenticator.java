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

package love.sola.fyoung.auth;

import love.sola.fyoung.util.ByteArrayUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class Authenticator {

	public static final String SECRET = "Eshore!@#";
	public static final MessageDigest DIGEST;

	static {
		try {
			DIGEST = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getAuthenticator(String token, Map<String, String> map) {
		String authenticator = map.get("clientip") + map.get("nasip") + map.get("mac") + map.get("timestamp");
		if (token != null) {
			authenticator += token;
		}
		authenticator += SECRET;
		return getMD5(authenticator);
	}

	public static String getMD5(String s) {
		byte[] buf = s.getBytes();
		DIGEST.update(buf);
		buf = DIGEST.digest();
		return ByteArrayUtil.bytesToHex(buf).toUpperCase();
	}


}
