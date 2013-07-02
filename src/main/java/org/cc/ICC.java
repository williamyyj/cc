package org.cc;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 10:35
 * To change this template use File | Settings | File Templates.
 *
 */
public interface ICC<E> {
    public Number num(E id);
    public Number num(E id, Number dv);
    public String str(E id);
    public String str(E id, String dv);
    public Date date(E id);
    public Date date(E id, Date dv);
    public boolean bool(E id);
    public boolean bool(E id, boolean dv);
    public Object obj(E id);
    public ICCList list(E id);
    public ICCMap map(E id);
    public int len();
    public boolean has(E id);
    public Object set(E id , Object value);
    public void del(E id);
    public String json();
}
