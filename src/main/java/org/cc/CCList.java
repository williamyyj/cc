package org.cc;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:36
 * To change this template use File | Settings | File Templates.
 */
public class CCList extends ArrayList<Object> implements ICCList {


    @Override
    public void set(Object value) {
        add(value);
    }

    @Override
    public Number num(Integer id) {
        return CC.num(this,id);
    }

    @Override
    public Number num(Integer id, Number dv) {
        return CC.num(this,id,dv);
    }

    @Override
    public String str(Integer id) {
        return CC.str(this,id) ;
    }

    @Override
    public String str(Integer id, String dv) {
        return CC.str(this,id,dv);
    }

    @Override
    public Date date(Integer id) {
        return CC.date(this,id);
    }

    @Override
    public Date date(Integer id, Date dv) {
        return CC.date(this,id,dv);
    }

    @Override
    public boolean bool(Integer id) {
        return CC.bool(this,id);
    }

    @Override
    public boolean bool(Integer id, boolean dv) {
        return CC.bool(this,id,dv);
    }

    @Override
    public Object obj(Integer id) {
        return get(id);
    }

    @Override
    public ICCList list(Integer id) {
        Object o = get(id);
        return (o instanceof ICCList) ? (ICCList) o : null ;
    }

    @Override
    public ICCMap map(Integer id) {
        Object o = get(id);
        return (o instanceof ICCMap) ? (ICCMap) o : null ;
    }

    @Override
    public int len() {
        return size();
    }

    @Override
    public boolean has(Integer id) {
        return (0<=id && id<size());
    }

    @Override
    public Object set(Integer id, Object value) {
        return this.set(id,value);
    }

    @Override
    public void del(Integer id) {
        this.remove(id);
    }

    @Override
    public String json() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
