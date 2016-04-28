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

package love.sola.fyoung.util;

import love.sola.fyoung.Client;
import love.sola.fyoung.log.OutputFormatter;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class NetUtil {

	public static final String IP_PATTERN = "[1-9][0-9]{1,2}\\.([0-9]{1,3}\\.){2}[1-9][0-9]{0,2}";
	private static final String os = System.getProperty("os.name").toLowerCase();

	public static String getLocalIP() {
		if (Client.config.clientIP != null) {
			return Client.config.clientIP;
		}
		try {
			return Client.config.clientIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			OutputFormatter.logTrace("Failed to get localhost IP address.", e);
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
		//os x use en0 insteadof eth0
		String prefix = os.contains("mac os x") ? "en" : "eth";
		try {
			Enumeration<NetworkInterface> it = NetworkInterface.getNetworkInterfaces();
			while (it.hasMoreElements()) {
				NetworkInterface iface = it.nextElement();
				if (!iface.getName().startsWith(prefix)) continue;
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
			OutputFormatter.logTrace("Failed to get MAC Address.", e);
		}
		return null;
	}

	public static final String TEST_URL = "http://www.msftncsi.com/ncsi.txt";

	public static boolean isInternet() {
		try {
			URL url = new URL(TEST_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setInstanceFollowRedirects(false);
			if (conn.getResponseCode() == 200) {
				Scanner in = new Scanner(conn.getInputStream());
				return in.nextLine().equals("Microsoft NCSI");
			}
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static String getPortal() throws IOException {
		URL url = new URL(TEST_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(false);
		if (conn.getResponseCode() == 302) {
			return conn.getHeaderField("location");
		} else {
			return null;
		}
	}

	public static void autoFetchIP() {
		try {
			autoFetchIP(getPortal());
		} catch (IOException e) {
			OutputFormatter.logTrace("Auto fetch Client/NAS IP failed.", e);
		}
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
			System.out.println("Name server has been set to: " + Client.config.specifiedDNS);
		}
	}

}
