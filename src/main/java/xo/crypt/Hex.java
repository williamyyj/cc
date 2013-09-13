/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xo.crypt;

/**
 * @author william
 */

public class Hex {
    static final char[] toHexMap={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	static final String hexToMap= "0123456789abcdef          ABCDEF";
	
    public static String toHex(byte[] b){
		StringBuffer sb = new StringBuffer(32);	
		for(int i=0; i < b.length; i++){
			int value = (b[i] & 0xFF);
			sb.append( toHexMap[(byte)(value>>4)]);
			sb.append( toHexMap[(byte)(value&0xf)]);
		}
		return sb.toString();	
	}
    
    public static byte[] hexTo(String s) {
        int l = s.length() / 2;
        byte data[] = new byte[l];
        int j = 0;
        for (int i = 0; i < l; i++) {
            char c = s.charAt(j++);
            int n, b;

            n = hexToMap.indexOf(c);
            b = (n & 0xf) << 4;
            c = s.charAt(j++);
            n = hexToMap.indexOf(c);
            b += (n & 0xf);
            data[i] = (byte) b;
        }
        return data;
    }
    
}
