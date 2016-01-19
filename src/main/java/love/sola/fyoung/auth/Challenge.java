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
@SuppressWarnings("Duplicates")
public class Challenge {

	public static final String URL = "http://enet.10000.gd.cn:10001/client/challenge";

	public static Map<String, String> configure(String u) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(Core.KEY_USERNAME, u);
		map.put(Core.KEY_CLIENT_IP, NetUtil.getLocalIP());
		map.put(Core.KEY_NAS_IP, Client.config.nasIP);
		map.put(Core.KEY_MAC, NetUtil.getMAC());
		map.put(Core.KEY_TIMESTAMP, Long.toString(System.currentTimeMillis()));
		map.put(Core.KEY_AUTHENTICATOR, Authenticator.getAuthenticator(null, map));
		return map;
	}

	public static String post(Map<String, String> conf) throws Exception {
		Map<String, String> map = Core.postWithRetry(URL, conf, 6);
		if (map.containsKey(Core.KEY_TOKEN)) {
			return map.get(Core.KEY_TOKEN);
		}
		return null;
	}

}
