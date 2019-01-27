package bgs99c.shared;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

public class Security {
	static Random random = new SecureRandom();
	public static byte createSalt() {
		return (byte)random.nextInt();
	}
	
	public static String saltHashString(String input, byte salt) {
		return saltData(Parser.fromHexString(input), salt);
	}
	public static String saltData(byte[] data, byte salt) {
		MessageDigest digest = null;
		try{
			digest = MessageDigest.getInstance("SHA-256");
			digest.update(data);
			digest.update(salt);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		byte[] hash = digest.digest();
		return Parser.toHexString(hash);
	}
	
	public static String saltUTFString(String input, byte salt) {
		return saltData(input.getBytes(), salt);
	}
}
