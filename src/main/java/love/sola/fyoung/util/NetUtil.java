package love.sola.fyoung.util;

import love.sola.fyoung.Client;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class NetUtil {

	public static final String IP_PATTERN = "[1-9][0-9]{1,2}\\.([0-9]{1,3}\\.){2}[1-9][0-9]{0,2}";

	public static String getLocalIP() {
		if (Client.config.clientIP != null) {
			return Client.config.clientIP;
		}
		try {
			return Client.config.clientIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
		}
		return null;
	}

	public static String getMAC() {
		if (Client.config.mac != null) {
			return Client.config.mac;
		}
		try {
			byte[] b = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuilder sb = new StringBuilder(18);
			char[] str = ByteArrayUtil.bytesToHex(b).toCharArray();
			for (int i = 0; i < str.length; i += 2) {
				sb.append(str[i]);
				sb.append(str[i + 1]);
				sb.append('-');
			}
			return sb.substring(0, sb.length() - 1);
		} catch (NullPointerException | SocketException | UnknownHostException e) {
			return getMac0();
		}
	}

	private static String getMac0() {
		try {
			Enumeration<NetworkInterface> it = NetworkInterface.getNetworkInterfaces();
			while (it.hasMoreElements()) {
				NetworkInterface iface = it.nextElement();
				if (!iface.getName().startsWith("eth")) continue;
				byte[] b = iface.getHardwareAddress();
				if (b == null) continue;
				StringBuilder sb = new StringBuilder(18);
				char[] str = ByteArrayUtil.bytesToHex(b).toCharArray();
				for (int i = 0; i < str.length; i += 2) {
					sb.append(str[i]);
					sb.append(str[i + 1]);
					sb.append('-');
				}
				return sb.substring(0, sb.length() - 1);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String TEST_URL = "http://www.baidu.com";

	public static boolean isInternet() {
		try {
			URL url = new URL(TEST_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setInstanceFollowRedirects(false);
			if (conn.getResponseCode() == 200) {
				return true;
			}
		} catch (IOException e) {
		}
		return false;
	}

	public static String getPortal() {
		try {
			URL url = new URL(TEST_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setInstanceFollowRedirects(false);
			if (conn.getResponseCode() == 302) {
				return conn.getHeaderField("location");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void autoFetchIP() {
		autoFetchIP(getPortal());
	}

	public static void autoFetchIP(String portal) {
		if (portal == null) {
			return;
		}
		Pattern pat = Pattern.compile(IP_PATTERN);
		Matcher mat = pat.matcher(portal);
		if (mat.find()) {
			Client.config.clientIP = mat.group();
			System.out.println("Fetched Client IP: " + Client.config.clientIP);
		}
		if (mat.find()) {
			Client.config.nasIP = mat.group();
			System.out.println("Fetched NAS IP: " + Client.config.nasIP);
		}
	}

	public static void resetDNS() {
		if (Client.config.specifiedDNS != null) {
			System.setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");
			System.setProperty("sun.net.spi.nameservice.nameservers", Client.config.specifiedDNS);
			System.out.println();
		}
	}

}
