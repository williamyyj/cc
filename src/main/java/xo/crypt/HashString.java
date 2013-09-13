package xo.crypt;

import java.util.zip.CRC32;

public class HashString {

	public static int toHashInt(String s) {
		byte[] buf = s.getBytes();
		int hash = 0;
		for (byte code : buf) {
			hash = ((hash << 7 - hash) + code ) ;
		}
		return hash ; //& 0x7fffffff;
	}

	public static long toHashLong(String s) {
		byte[] b = s.getBytes();
		long hash = 0;
		for (byte code:b) {
			hash = hash << 12 | ((hash >>> 52) ^ code);
		}
		return (hash & 0x7fffffffffffffffL);

	}

	public static int toHash(String s) {
		byte[] b = s.getBytes();
		int hash = 0;
		int code = 0;
		for (int i = 0; i < b.length; i++) {
			code = b[i] & 0xff;
			hash = hash * 31 + code;
		}
		return hash;
	}

	public static int toCRC32(String s) {
		CRC32 crc = new CRC32();
		crc.update(s.getBytes());
		return (int) crc.getValue();
	}

	public static long toCRC64(String s) {
		Crc64 crc = new Crc64();
		crc.reset();
		crc.update(s.getBytes());
		return crc.value & 0x7fffffffffffffffL;
	}

	public static int myHashCode(String s) {
		byte[] val = s.getBytes();
		int hash = 0;
		for (byte b:val) {
			hash = (hash << 5) - hash + b;
		}
		return hash;
	}

	public static void main(String[] args) {
		String url1 = "http://pic.bandaonews.com/PicView.aspx?id=37219";
		String url2 = "http://tech.163.com/05/0829/09/1SAIIRG8000915BD.html";
		System.out.println(url1.hashCode()+"-----"+url2.hashCode());
		// for(int i=0;i<100;i++){
		String test = "com.hw.web.test.jsso.page";
		System.out.println("CRC64:" + HashString.toCRC64(test));
		System.out.println("CRC32:" + HashString.toCRC32(test));
		System.out.println("HashLong:" + HashString.toHashLong(test));
		System.out.println("HashInt:" + HashString.toHashInt(url1)+"----"+HashString.toHashInt(url2));
		System.out.println("url.hashCode():" + url1.hashCode());
		System.out.println("MY Hash:" + HashString.myHashCode(url1));
		
		System.out.println("c".hashCode());
		System.out.println("d".hashCode());
		System.out.println("e".hashCode());
		System.out.println("f".hashCode());

		// System.out.println(test.hashCode());
		// System.out.println(0^1234);
		// }
	}

}
