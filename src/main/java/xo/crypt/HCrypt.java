/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.crypt;

/**
 *
 * @author william
 */
public class HCrypt {
    
    //加密程式  
    public String encode(String password) {
        byte[] old = password.getBytes();
        byte[] ret = new byte [old.length];
        for (int i = 0; i < old.length; ++i) {
            ret[i] = (byte) (old[i]  - (i + 3) % 5);
        }
        return new String(ret);
    }

    //解密程式
    public String decode(String password) {
        char[] old = password.toCharArray();
        char[] ret = new char[old.length];
        for (int i = 0; i < old.length; ++i) {
            //ret[i] = (char) (old[i] - ((i+3) % 5)) ; 
            ret[i] = (char) ((int)old[i] + (i + 3) % 5);
        }
        return new String(ret);
    }

    public static void main(String[] args) {
        HCrypt ende = new HCrypt();
        String pwd1 = ".u88H719jhUc6";
        String pwd2 = "MydAc0p6w9Ft2";
        String hash3 = ende.encode("1qaz2wsx");
        System.out.println(ende.decode("1qaz2wsx"));
        System.out.println(ende.decode(hash3));
        System.out.println(ende.decode(pwd1));
        System.out.println(ende.decode(pwd2));
    }
    
}
