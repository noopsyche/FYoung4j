package love.sola.fyoung.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import love.sola.fyoung.config.Config;
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
public class Active {

	public static final String URL = "http://enet.10000.gd.cn:8001/hbservice/client/active";

	public static Map<String, String> configure(String u) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(Core.KEY_USERNAME, u);
		map.put(Core.KEY_CLIENT_IP, NetUtil.getLocalIP());
		map.put(Core.KEY_NAS_IP, Config.I.nasIP);
		map.put(Core.KEY_MAC, NetUtil.getMAC());
		map.put(Core.KEY_TIMESTAMP, Long.toString(System.currentTimeMillis()));
		map.put(Core.KEY_AUTHENTICATOR, Authenticator.getAuthenticator(null, map));
		return map;
	}

	public static String post(Map<String, String> conf) throws Exception {
		String response = Core.get(URL, conf);
		Map<String, String> map = new Gson().fromJson(response, new TypeToken<Map<String, String>>() {}.getType());
		if (map.containsKey(Core.KEY_RES_INFO)) {
			return map.get(Core.KEY_RES_INFO);
		}
		return null;
	}


}
