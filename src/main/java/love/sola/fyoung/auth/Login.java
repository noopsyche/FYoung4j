package love.sola.fyoung.auth;

import love.sola.fyoung.Client;
import love.sola.fyoung.util.NetUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Login {

	public static final String URL = "http://enet.10000.gd.cn:10001/client/login";

	public static Map<String, String> configure(String u, String p, String t, boolean isWiFi) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(Core.KEY_USERNAME, u);
		map.put(Core.KEY_PASSWORD, p);
		map.put(Core.KEY_CLIENT_IP, NetUtil.getLocalIP());
		map.put(Core.KEY_NAS_IP, Client.config.nasIP);
		map.put(Core.KEY_MAC, NetUtil.getMAC());
		map.put(Core.KEY_TIMESTAMP, Long.toString(System.currentTimeMillis()));
		map.put(Core.KEY_AUTHENTICATOR, Authenticator.getAuthenticator(t, map));
		map.put(Core.KEY_IS_WIFI, isWiFi ? Core.IS_WIFI : Core.NOT_WIFI);
		return map;
	}

	public static String post(Map<String, String> conf) throws Exception {
		Map<String, String> map = Core.postWithRetry(URL, conf, 6);
		if (map.containsKey(Core.KEY_RES_INFO)) {
			return map.get(Core.KEY_RES_INFO);
		}
		return null;
	}

}
