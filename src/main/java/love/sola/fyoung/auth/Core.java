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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import love.sola.fyoung.Client;
import love.sola.fyoung.log.OutputFormatter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class Core {

	public static final String IS_WIFI = "4060";
	public static final String NOT_WIFI = "1050";
	public static final String KEY_NAS_IP = "nasip";
	public static final String KEY_CLIENT_IP = "clientip";
	public static final String KEY_MAC = "mac";
	public static final String KEY_TIMESTAMP = "timestamp";
	public static final String KEY_AUTHENTICATOR = "authenticator";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_IS_WIFI = "iswifi";
	public static final String KEY_TOKEN = "challenge";
	public static final String KEY_RES_INFO = "resinfo";

	protected static Gson gson = new Gson();

	public static Map<String, String> get(String target, Map<String, String> conf) throws Exception {
		URL url = new URL(formatURLParam(target, conf));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);
		conn.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuilder html = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			html.append(inputLine);
		}
		Map<String, String> map = gson.fromJson(html.toString(), new TypeToken<Map<String, String>>() { }.getType());
		if (map == null) {
			throw new RuntimeException("Failed parse response to map: " + html.toString());
		}
		return map;
	}

	public static Map<String, String> post(String target, Map<String, String> conf) throws Exception {
		URL url = new URL(target);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(1000);
		conn.setReadTimeout(1000);
		conn.setRequestMethod("POST");
		conn.addRequestProperty("User-Agent", "Mozilla");
		conn.addRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
		writer.write(gson.toJson(conf));
		writer.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuilder html = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			html.append(inputLine);
		}
		in.close();
		Map<String, String> map = gson.fromJson(html.toString(), new TypeToken<Map<String, String>>() { }.getType());
		if (map == null) {
			throw new RuntimeException("Failed parse response to map: " + html.toString());
		}
		if (Client.config.debugMode) {
			System.out.println("Response: " + map);
		}
		return map;
	}

	public static Map<String, String> postWithRetry(String target, Map<String, String> conf, int retry) throws Exception {
		Throwable cause = null;
		for (int i = 0; i < retry; i++) {
			try {
				return post(target, conf);
			} catch (SocketTimeoutException e) {
				cause = e; //We only consider the final exception is the real one
				OutputFormatter.logTrace("Retry " + (i + 1) + "failed.", e);
			}
		}
		throw new PostException(cause);
	}

	private static String formatURLParam(String url, Map<String, String> conf) {
		url += '?';
		for (Map.Entry<String, String> entry : conf.entrySet()) {
			try {
				url += URLEncoder.encode(entry.getKey(), "UTF-8") + '=' + URLEncoder.encode(entry.getValue(), "UTF-8") + '&';
			} catch (UnsupportedEncodingException e) {
			}
		}
		return url.substring(0, url.length() - 1);
	}

}
