package org.cc;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 10:39
 * To change this template use File | Settings | File Templates.
 */
public interface ICCFmt<E> {
    public E strToObj(String text) ;
    public String objToStr(E value);
}
