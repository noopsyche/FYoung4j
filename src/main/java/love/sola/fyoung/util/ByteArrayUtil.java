package love.sola.fyoung.util;

/**
 * ***********************************************
 * Created by Sola on 2014/6/16.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class ByteArrayUtil {

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	public static boolean arrayEqual(byte[] src, int srcOffset, byte[] dest, int destOffset, int length) {
		for (int i = 0; i < length; i++) {
			if (src[srcOffset] != dest[destOffset]) {
				return false;
			}
			srcOffset++;
			destOffset++;
		}
		return true;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
