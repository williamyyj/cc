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
	public int asInt(E id) ; 
	public int asInt(E id , int dv);
	public long asLong(E id) ; 
	public long asLong(E id , int dv);
	public double asDouble(E id) ; 
	public double asDouble(E id , int dv);
    public String asString(E id);
    public String asString(E id, String dv);
    public Date asDate(E id);
    public Date asDate(E id, Date dv);
    public boolean asBoolean(E id);
    public boolean asBoolean(E id, boolean dv);
    public Object obj(E id);
    public ICCList list(E id);
    public ICCMap map(E id);
    public int len();
    public boolean has(E id);
    public Object set(E id, Object value);
    public void del(E id);
    public String json();
    public String json(String indent);
    public String json(String base,String indent);
    public void setIsIndent(boolean isIndent);
}
