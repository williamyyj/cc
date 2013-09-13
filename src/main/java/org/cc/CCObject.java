package org.cc;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/3
 * Time: 下午 6:16
 * To change this template use File | Settings | File Templates.
 */

public class CCObject extends CCMap {

    public CCObject(){
        super();
    }
	
    public boolean isAttr(String id){
        return (id!=null && id.charAt(0)=='@');
    }
	


}
