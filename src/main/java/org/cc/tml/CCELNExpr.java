package org.cc.tml;

import java.util.Map;

/**
 * @author william 標準結點
 * 執行期結點
 */
public class CCELNExpr implements ICCEL {

    protected String[] args;

    public CCELNExpr(String... args) {
        this.args = args;
    }

    public void processTemplate(ICCVM el, Map<String,Object> m) throws Exception {
        //out.println(text);
    }

    public String source() {
        StringBuilder sb = new StringBuilder();
        sb.append(ICCEL.el_prefix);
        for (String arg : args) {
            sb.append(arg).append(' ');
        }
        sb.append(ICCEL.el_suffix);
        return sb.toString();
    }

    public void set_address(int adderss) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
