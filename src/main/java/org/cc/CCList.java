package org.cc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:36
 * To change this template use File | Settings | File Templates.
 */
public class CCList extends ArrayList<Object> implements ICCList {

    protected boolean isIndent = true;

    public CCList() {

    }

    public CCList(boolean isIndent) {
        this.isIndent = isIndent;
    }

    public CCList(Object array) {
        super();
        if (array.getClass().isArray()) {
            int length = Array.getLength(array);
            for (int i = 0; i < length; i += 1) {
                add(Array.get(array, i));
            }
        } else {
            throw new RuntimeException(
                    "JSONArray initial value should be a string or collection or array.");
        }
    }

    public CCList(Collection c) {
        super(c);
    }


    @Override
    public void set(Object value) {
        add(value);
    }

    @Override
    public Number num(Integer id) {
        return CC.num(this, id);
    }

    @Override
    public Number num(Integer id, Number dv) {
        return CC.num(this, id, dv);
    }

    @Override
    public String str(Integer id) {
        return CC.str(this, id);
    }

    @Override
    public String str(Integer id, String dv) {
        return CC.str(this, id, dv);
    }

    @Override
    public Date date(Integer id) {
        return CC.date(this, id);
    }

    @Override
    public Date date(Integer id, Date dv) {
        return CC.date(this, id, dv);
    }

    @Override
    public boolean bool(Integer id) {
        return CC.bool(this, id);
    }

    @Override
    public boolean bool(Integer id, boolean dv) {
        return CC.bool(this, id, dv);
    }

    @Override
    public Object obj(Integer id) {
        return get(id);
    }

    @Override
    public ICCList list(Integer id) {
        Object o = get(id);
        return (o instanceof ICCList) ? (ICCList) o : null;
    }

    @Override
    public ICCMap map(Integer id) {
        Object o = get(id);
        return (o instanceof ICCMap) ? (ICCMap) o : null;
    }

    @Override
    public int len() {
        return size();
    }

    @Override
    public boolean has(Integer id) {
        return (0 <= id && id < size());
    }

    @Override
    public Object set(Integer id, Object value) {
        return this.set(id, value);
    }

    @Override
    public void del(Integer id) {
        this.remove(id);
    }

    @Override
    public String json() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (Object item : this) {
            sb.append(CC.json(item));
        }
        if (size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        sb.append(']');
        return sb.toString();
    }


    public String json(String indent) {
        return json(null, indent);
    }

    public String json(String base, String indent) {
        if (!isIndent) {
            return json();
        }
        StringBuilder sb = new StringBuilder();
        String next = (base == null) ? indent : base + indent;
        sb.append("[\n");
        for (int i = 0; i < size(); i++) {
            CC.indent(sb, next, CC.json(get(i), next, indent));
            sb.append(",\n");
        }
        if (size() > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append('\n');
        CC.indent(sb, base, "]");
        return sb.toString();
    }

    @Override
    public void setIsIndent(boolean isIndent) {
        this.isIndent = isIndent;
    }


}
