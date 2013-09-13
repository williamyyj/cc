package xo.crypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Crypt {
	private static String Algorithm = "DES"; // DES,DESede,Blowfish

	private static boolean debug = false ;

	static char[] FHexMap = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };

	public static String bytes2Hex(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (byte b : buf) {
			sb.append(FHexMap[(byte) ((b & 0xff) >> 4)]);
			sb.append(FHexMap[(byte) (b & 0xf)]);
		}
		return sb.toString();
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}


	public static byte[] encode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		if (debug) {
			System.out.println("input as bytes:" + byte2hex(input));
			System.out.println("input as String:" + new String(input));
		}
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		if (debug)
			System.out.println("encode :" + byte2hex(cipherByte));
		return cipherByte;
	}

	public static byte[] decode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		if (debug)
			System.out.println("decode :" + byte2hex(input));
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		if (debug) {
			System.out.println("decode as bytes:" + byte2hex(clearByte));
			System.out.println("decode as String:" + (new String(clearByte)));
		}
		return clearByte;
	}

	public static byte[] getKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
		SecretKey deskey = keygen.generateKey();
		if (debug) System.out.println("key:" + byte2hex(deskey.getEncoded()));
		return deskey.getEncoded();
	}

	public static void main(String[] args) throws Exception {
		debug = true;
		byte[] key = getKey();
		byte[] str = encode("這是一個測驗".getBytes(), key);
		decode(str, key);
	}
}
