package org.cc;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:23
 * To change this template use File | Settings | File Templates.
 */

public interface ICCList extends ICC<Integer>{
    public void set(Object value);
	public List<Object> model();
}
