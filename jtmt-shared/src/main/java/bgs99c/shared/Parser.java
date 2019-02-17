package bgs99c.shared;

import java.util.function.Function;

public class Parser {
	public static String toHexString(byte[] arr) {
		Function<Integer, Character> getHex = (i) -> {
			if (i<10) return (char)('0'+i);
			else return (char) ('A' + i - 10);
		};
		StringBuilder sb = new StringBuilder(arr.length * 2);
		for (byte b : arr) {
			sb.append(getHex.apply((b & 0xF0) >> 4).charValue());
		    sb.append(getHex.apply(b & 0x0F).charValue());
		}
		String hex = sb.toString();
		return hex;
	}
	public static byte[] fromHexString(String encoded) {
		try {
		    byte result[] = new byte[encoded.length()/2];
		    char enc[] = encoded.toCharArray();
		    for (int i = 0; i < enc.length; i += 2) {
		        StringBuilder curr = new StringBuilder(2);
		        curr.append(enc[i]).append(enc[i + 1]);
		        result[i/2] = (byte) Integer.parseInt(curr.toString(), 16);
		    }
		    return result;
		} catch (NumberFormatException e) {
			System.err.println("Wrong hex format : " + encoded);
			throw e;
		}
	}
}
