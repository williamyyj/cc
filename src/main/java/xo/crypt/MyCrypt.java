package xo.crypt;

import java.security.MessageDigest;

/**
 *
 * @author yinwilliam
 */
public class MyCrypt {

	public static String getCryptText(String Source_Str) {
		String hashdata = "";
		String Algorithm = "SHA-1";
		try {
			Base64 b64 = new Base64();

			MessageDigest hashalgorithm = MessageDigest.getInstance(Algorithm);
			hashalgorithm.update(Source_Str.getBytes());
			byte[] digest = hashalgorithm.digest();
			hashdata = new String(b64.b64_encode(digest));
			hashdata = hashdata.substring(0, (hashdata.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashdata;
	}

	public static void main(String[] args) {
		String hash1 = getCryptText("76543211111111111111111111111111111111111111111111111111111111111111111111");
		System.out.println(hash1);
		String hash2 = getCryptText("7654321111111111111111111111111111111111112");
		System.out.println(hash2);
		String hash3 = getCryptText("7654321111111111111111111111111111111111113");
		System.out.println(hash3);
	}
}
