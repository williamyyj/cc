package org.cc;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:25
 * To change this template use File | Settings | File Templates.
 */
public interface ICCMap extends ICC<String> {
    public Object[] toArray(String... names);
	public Map<String,Object> model();
	public Set<String> names();
	public Object ccpath(String query);
	public void ccpath(String query , Object value);
}
