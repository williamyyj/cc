package org.cc;

import java.util.Date;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:27
 * To change this template use File | Settings | File Templates.
 */
public class CCObject extends HashMap<String,Object> implements ICCMap {


    @Override
    public Object[] toArray(String... name) {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Number num(String id) {
        return CC.num(this,id);
    }

    @Override
    public Number num(String id, Number dv) {
        return CC.num(this,id,dv);
    }

    @Override
    public String str(String id) {
        return CC.str(this, id);
    }

    @Override
    public String str(String id, String dv) {
        return CC.str(this,id,dv);
    }

    @Override
    public Date date(String id) {
        return CC.date(this, id);
    }

    @Override
    public Date date(String id, Date dv) {
        return CC.date(this,id,dv);
    }

    @Override
    public boolean bool(String id) {
        return CC.bool(this,id);
    }

    @Override
    public boolean bool(String id, boolean dv) {
        return CC.bool(this,id,dv);
    }

    @Override
    public Object obj(String id) {
        return get(id);
    }

    @Override
    public ICCList list(String id) {
        Object o = get(id);
        return (o instanceof ICCList) ? (ICCList) o : null ;
    }

    @Override
    public ICCMap map(String id) {
        Object o = get(id);
        return (o instanceof ICCMap) ? (ICCMap) o : null ;
    }

    @Override
    public int len() {
        return size();
    }

    @Override
    public boolean has(String id) {
        return this.containsKey(id);
    }

    @Override
    public Object set(String id, Object value) {
        return put(id,value);
    }

    @Override
    public void del(String id) {
       remove(id);
    }

    @Override
    public String json() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

	public int asInt(String id) {
		return CC.asInt(this, id);
	}

	public int asInt(String id, int dv) {
		return CC.asInt(this, id, dv);
	}

	public long asLong(String id) {
		return CC.asLong(this, id);
	}

	public long asLong(String id, long dv) {
		return CC.asLong(this, id, dv);
	}

	public double asDouble(String id) {
		return CC.asDouble(this, id);
	}

	public double asDouble(String id, double dv) {
		return CC.asDouble(this,id,dv);
	}

}
