package love.sola.fyoung.auth;

import love.sola.fyoung.util.ByteArrayUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
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
