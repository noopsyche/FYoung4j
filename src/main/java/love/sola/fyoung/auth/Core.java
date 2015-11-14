package love.sola.fyoung.auth;

import com.google.gson.Gson;
import love.sola.fyoung.log.DebugLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Core {

	public static final String IS_WIFI = "1050";
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

	public static String postJson(String target, Map<String, String> conf) throws Exception {
		URL url = new URL(target);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(500);
		conn.setReadTimeout(500);
		conn.addRequestProperty("User-Agent", "Mozilla");
		conn.addRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		writer.write(new Gson().toJson(conf));
		writer.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuilder html = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			html.append(inputLine);
		}
		in.close();
		return html.toString();
	}

	public static String postJsonWithRetry(String target, Map<String, String> conf, int retry) throws Exception {
		for (; retry > 0; retry--) {
			try {
				return postJson(target, conf);
			} catch (Exception e) {
				System.out.println("Failed. Retry: " + retry);
				DebugLogger.logTrace("Failed Trace: ", e);
			}
		}
		return null;
	}

	public static String get(String target, Map<String, String> conf) throws Exception {
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
		return html.toString();
	}

	public static String formatURLParam(String url, Map<String, String> conf) {
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
