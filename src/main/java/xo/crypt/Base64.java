package xo.crypt;

public class Base64 {
    // std base64

    private static final byte[] b64_entb = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };
    private static final byte[] u64_entb = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };
    //private static final char[] b64_entb = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    //private static final char[] u64_entb = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
    private static final byte[] detb = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54,
        55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
        5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
        24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34,
        35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51
    };
    private static final byte PAD = '=';
    private static final int line_size = 80;

    public byte[] b64_encode(byte[] raw) {
        //byte[] buf = new byte[(length + 2) / 3 * 4 + length / 72];
        int len = raw.length;
        byte[] buf = new byte[(len + 2) / 3 * 4];
        int c1, c2, c3;
        int i = 0;
        int idx = 0;
        while (i < len) {
            c1 = raw[i++] & 0xff;
            buf[idx++] = b64_entb[c1 >> 2];
            if (i == len) {
                buf[idx++] = b64_entb[(c1 & 0x3) << 4];
                buf[idx++] = PAD;
                buf[idx++] = PAD;
                break;
            }
            c2 = raw[i++] & 0xff;
            if (i == len) {
                buf[idx++] = b64_entb[(c1 & 0x3) << 4 | (c2 & 0xf0) >> 4];
                buf[idx++] = b64_entb[(c2 & 0xf) << 2];
                buf[idx++] = PAD;
                break;
            }
            c3 = raw[i++] & 0xff;
            buf[idx++] = b64_entb[(c1 & 0x3) << 4 | (c2 & 0xf0) >> 4];
            buf[idx++] = b64_entb[(c2 & 0xf) << 2 | (c3 & 0xc0) >> 6];
            buf[idx++] = b64_entb[c3 & 0x3f];
        }
        return buf;
    }

    public byte[] u64_encode(byte[] raw) {
        //byte[] buf = new byte[(length + 2) / 3 * 4 + length / 72];
        int len = raw.length;
        int size = (len + 2) / 3 * 4;
        int mod = len % 3;
        if(mod==1){
            size -= 2;
        } else if(mod==2){
            size--;
        }
        byte[] buf = new byte[size];
        int c1, c2, c3;
        int i = 0;
        int idx = 0;
        while (i < len) {
            c1 = raw[i++] & 0xff;
            buf[idx++] = u64_entb[c1 >> 2];
            if (i == len) {
                buf[idx++] = u64_entb[(c1 & 0x3) << 4];
                //buf[idx++] = PAD;
                //buf[idx++] = PAD;
                break;
            }
            c2 = raw[i++] & 0xff;
            if (i == len) {
                buf[idx++] = u64_entb[(c1 & 0x3) << 4 | (c2 & 0xf0) >> 4];
                buf[idx++] = u64_entb[(c2 & 0xf) << 2];
                //buf[idx++] = PAD;
                break;
            }
            c3 = raw[i++] & 0xff;
            buf[idx++] = u64_entb[(c1 & 0x3) << 4 | (c2 & 0xf0) >> 4];
            buf[idx++] = u64_entb[(c2 & 0xf) << 2 | (c3 & 0xc0) >> 6];
            buf[idx++] = u64_entb[c3 & 0x3f];
        }
        return buf;
    }

    public byte[] u64_decode(String base64) {
        char[] buf = base64.toCharArray();
        int len = buf.length;
        byte[] raw = new byte[len * 6 / 8];
        int idx = 0;
        int c1, c2, c3, c4;
        int i = 0;

        while (i < len) {
            c1 = detb[buf[i++]];
            c2 = detb[buf[i++]];
            
            raw[idx++] = (byte) ((c1 << 2) | (c2 & 0x30) >> 4);
            if (i>=len || (c3 = detb[buf[i++]]) == -1) {
                break;
            }
            
            raw[idx++] = (byte) (((c2 & 0xf) << 4) | ((c3 & 0x3c) >> 2));
            if (i>=len || (c4 = detb[buf[i++]]) == -1) {
                break;
            }
            raw[idx++] = (byte) (((c3 & 3) << 6) | c4);
        }
        return raw;
    }

    public byte[] b64_decode(String base64) {
        char[] buf = base64.toCharArray();
        int len = buf.length;
        int size = len * 6 / 8;
        //size = (mod==0) ? size : size - mod+1;
        int pad = 0;
        for (int i = buf.length - 1; buf[i] == '='; i--) {
            pad++;
        }

        byte[] raw = new byte[size - pad];
        int idx = 0;
        int c1, c2, c3, c4;
        int i = 0;

        while (i < len) {
            c1 = detb[buf[i++]];
            c2 = detb[buf[i++]];
            raw[idx++] = (byte) ((c1 << 2) | (c2 & 0x30) >> 4);
            if ((c3 = detb[buf[i++]]) == -1) {
                break;
            }
            raw[idx++] = (byte) (((c2 & 0xf) << 4) | ((c3 & 0x3c) >> 2));
            if ((c4 = detb[buf[i++]]) == -1) {
                break;
            }
            raw[idx++] = (byte) (((c3 & 3) << 6) | c4);
        }
        return raw;
    }

    public static void main(String[] args) throws Exception {
        String text = " {\"mail\":{\"passwd\":\"1q2w9o0p\",\"host\":\"mail.hyweb.com.tw\",\"iprotocol\":\"pop3\",\"from\":\"admin@hyweb.com.tw\",\"disp\":\"系統管理員\",\"user\":\"williamyyj@tpe.hyweb.com.tw\",\"ihost\":\"mail.hyweb.com.tw\",\"spa\":\"\"},\"schedule\":{\"min\":\"30\",\"sch3\":\"0\",\"sch2\":\"0\",\"sch1\":\"0\",\"hour\":\"20\",\"sch\":\"m3\",\"sch5\":\"0\",\"sch4\":\"4\"},\"@act\":\"jo\",\"epaper\":{\"vol\":\"vol\",\"title\":\"電子報測試\",\"status\":\"1\",\"sd\":\"sd\"},\"cond\":\",n1,n2,n3,,\"}xxxx";
        byte[] src = text.getBytes("UTF-8");
        //String a_enc =new String(org.apache.commons.codec.binary.Base64.encodeBase64URLSafe(src));
        //System.out.println(a_enc);
        System.out.println("====[" + text + "]====");
        Base64 b64 = new Base64();
        String m_enc = new String(b64.u64_encode(src));
        System.out.println("====[" + m_enc + "]====");
        String m_ret = new String(b64.u64_decode(m_enc), "UTF-8");
        System.out.println("====[" + m_ret + "]====");
    }
}
