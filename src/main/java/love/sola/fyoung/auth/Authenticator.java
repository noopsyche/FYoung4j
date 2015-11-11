package love.sola.fyoung.auth;

import love.sola.fyoung.util.ByteArrayUtil;

import java.security.MessageDigest;
import java.util.Map;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Authenticator {

	public static final String SECRET = "Eshore!@#";


	public static String getAuthenticator(String token, Map<String, String> map) {
		String authenticator = map.get("clientip") + map.get("nasip") + map.get("mac") + map.get("timestamp");
		if (token != null) {
			authenticator += token;
		}
		authenticator += SECRET;
		return getMD5(authenticator);
	}

	public static String getMD5(String s) {
		try {
			byte[] buf = s.getBytes();
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(buf);
			buf = messagedigest.digest();
			return ByteArrayUtil.bytesToHex(buf).toUpperCase();
		} catch (Exception e) {
		}
		return null;
	}


}
