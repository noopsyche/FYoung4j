package love.sola.fyoung.auth;

import love.sola.fyoung.Client;
import love.sola.fyoung.util.NetUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@SuppressWarnings("Duplicates")
public class Active {

	public static final String URL = "http://enet.10000.gd.cn:8001/hbservice/client/active";

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
		Map<String, String> map = Core.get(URL, conf);
		if (map.containsKey(Core.KEY_RES_INFO)) {
			return map.get(Core.KEY_RES_INFO);
		}
		return null;
	}


}
