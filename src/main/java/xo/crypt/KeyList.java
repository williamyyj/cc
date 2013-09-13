package xo.crypt;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Enumeration;

public class KeyList {
	public static void main(String[] args){
		try {
		    // Load the keystore in the user's home directory
		    //File file = new File(System.getProperty("user.home") + File.separatorChar + ".keystore");
			File file = new File("resources/app/baphiq/baphiq.jks");
		    FileInputStream is = new FileInputStream(file);
		    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		    String password = "okpass";
		    keystore.load(is, password.toCharArray());

		    // List the aliases
		    Enumeration e = keystore.aliases();
		    for (; e.hasMoreElements(); ) {
		        String alias = (String)e.nextElement();
		        System.out.println("===== alias : " + alias);
		        // Does alias refer to a private key?
		        System.out.println("===== isKeyEntry  : " + keystore.isKeyEntry(alias));
		        System.out.println("===== isCertificateEntry  : "+ keystore.isCertificateEntry(alias));

		    }
		    is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
