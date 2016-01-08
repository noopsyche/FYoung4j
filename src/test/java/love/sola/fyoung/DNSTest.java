package love.sola.fyoung;

import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * ***********************************************
 * Created by Sola on 2016/1/8.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class DNSTest {

	@Test
	public void test() throws UnknownHostException {
		resetDNS();
		System.out.println(InetAddress.getByName("enet.10000.gd.cn").getHostAddress());
		System.out.println(new InetSocketAddress("baidu.com", 80).getAddress().getHostAddress());
		System.out.println(new InetSocketAddress("enet.10000.gd.cn", 80).getAddress().getHostAddress());
		System.out.println(InetAddress.getByName("baidu.com").getHostAddress());
	}

	public static void resetDNS() {
		System.setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");
		System.setProperty("sun.net.spi.nameservice.nameservers", "202.96.128.166,202.96.134.133");
	}

}
