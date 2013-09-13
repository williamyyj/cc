package xo.crypt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class NewKeyStore {
	public static void main(String[] args) throws Exception{
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileOutputStream fos = new FileOutputStream("pest.jks");
		char[] pwd = "okpass".toCharArray();
		ks.load(null, pwd);
		ks.store(fos, pwd);
		fos.close();
	}
}
