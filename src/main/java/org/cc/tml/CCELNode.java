package org.cc.tml;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author william
 */
public class CCELNode {
    
    private long lastModified ; 
    private File src ; 
    private ICCEL[] data ; 
    
    
    public CCELNode(File src){
        this.src = src;
    }
    
    public long lastModified(){
        return this.lastModified;
    }
    
    public void reload(){
        //System.out.println("===== src : " + src);
        //System.out.println("===== src : " + new File(".").getAbsolutePath());
        if(src.exists() && src.lastModified() > lastModified){
            data = null ; // gc 
            try {
                data = new CCELParser(src,"UTF-8").parser();
                this.lastModified = src.lastModified();
            } catch (Exception ex) {
                Logger.getLogger(CCELNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lastModified = src.lastModified();
    }
    
    
    public ICCEL[] toArray(){
        reload();
        return data;
    }
    
}
