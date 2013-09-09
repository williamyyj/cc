package org.cc;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:25
 * To change this template use File | Settings | File Templates.
 */
public interface ICCMap extends ICC<String> {
    public Object[] toArray(String... names);
    public Object attr(String id);
    public Object attr(String id, Object dv);
}
