package xo.crypt;

import java.security.cert.Certificate;
import java.security.*;
import java.io.File;
import java.io.FileInputStream;

import sun.misc.BASE64Encoder;
 

public class ExportPriv {
	
	String fKeyStore = "resources/app/baphiq/baphiq.jks";
    public static void main(String[] args) throws Exception{
	ExportPriv myep = new ExportPriv();
	myep.doit();
    }
 
    public void doit() throws Exception{
 
	KeyStore ks = KeyStore.getInstance("JKS");
	String fileName = fKeyStore;
	String alias = "pest";
	char[] passPhrase = "okpass".toCharArray();

	
 
	File certificateFile = new File(fileName);
	ks.load(new FileInputStream(certificateFile), passPhrase);
	
	   Key key = ks.getKey(alias, passPhrase);
       System.out.println("Key algorithm: "+key.getAlgorithm());
       System.out.println("Key format: "+key.getFormat());
 
	KeyPair kp = getPrivateKey(ks, alias, passPhrase);
		
	PrivateKey privKey = kp.getPrivate();
	
	//String b64 = Base64.encode(privKey.getEncoded());
    String b64 = new BASE64Encoder().encode(privKey.getEncoded());
	System.out.println("-----BEGIN PRIVATE KEY-----");
	System.out.println(b64);
	System.out.println("-----END PRIVATE KEY-----");
 
	}
 
// From http://javaalmanac.com/egs/java.security/GetKeyFromKs.html
 
   public KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            // Get private key
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);
    
                // Get public key
                PublicKey publicKey = cert.getPublicKey();
    
                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey)key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }
 
}