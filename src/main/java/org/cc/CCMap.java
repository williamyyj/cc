package org.cc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:27
 * To change this template use File | Settings | File Templates.
 */
public class CCMap extends HashMap<String,Object> implements ICCMap {

    protected boolean isIndent = true ;
	
    public CCMap(){

    }

    public CCMap(boolean isIndent){
        this.isIndent = isIndent;
        //this.put("$indent",isIndent);
    }

    public CCMap(Map m){
        this.putAll(m);
    }

    @Override
    public Object[] toArray(String... names) {
        if (names!=null){
            Object[] ret = new Object[names.length];
            int idx = 0;
            for(String name : names){
                 ret[idx] = get(name);
            }
            return ret ;
        }
        return new Object[]{} ;
    }

    @Override
    public Object attr(String id) {
        return obj('@'+id);
    }

    @Override
    public Object attr(String id, Object dv) {
       Object o = get('@'+id);
        return (o!=null) ? o : dv ;
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
        StringBuilder sb = new StringBuilder();
        sb.append('{') ;
        Set<Map.Entry<String,Object>> es = this.entrySet();
        for(Map.Entry<String,Object> e : es){
            sb.append('"').append(e.getKey()).append('"').append(':').append(CC.json(e.getValue())).append(',');
        }
        if(es.size()>0) sb.setLength( sb.length()-1);
        sb.append('}') ;
        return sb.toString();
    }




    @Override
    public String json(String indent) {
        return json(null, indent);
    }

    public String json(String base, String indent) {
        if(!isIndent){
            return json();
        }
        StringBuilder sb = new StringBuilder();
        String next = (base != null) ? base + indent : indent;
        sb.append("{\n");
        Set<Map.Entry<String,Object>> es = this.entrySet();
        for(Map.Entry<String,Object> e : es){
            CC.indent(sb,next,CC.json(e.getKey()));
            sb.append(" : ").append(CC.json(e.getValue(), next, indent));
            sb.append(",\n");
        }
        if (len() > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append('\n');
        CC.indent(sb, base, "}");
        return sb.toString();
    }

    @Override
    public void setIsIndent(boolean isIndent) {
        this.isIndent = isIndent;
    }

	public int toI(String id) {
		return CC.asInt(this, id) ;
	}

	public int toI(String id, int dv) {
		return CC.asInt(this, id, dv);
	}

	public long toL(String id) {
		return CC.asLong(this, id);
	}

	public long toL(String id, int dv) {
		return CC.asLong(this, id, dv);
	}

	public double toF(String id) {
		return CC.asDouble(this, id) ;
	}

	public double toF(String id, int dv) {
		return CC.asDouble(this, id, dv);
	}
	
	public Map<String,Object> model(){
		return this ; 
	}
	
	public void disable_indent(String id) {
		ICCList list = list(id);
		if(list !=null){
			int len = list.len();
			for(int i=0; i<len ; i++){
				Object o = list.obj(i);
				if(o instanceof ICC){
					((ICC)o).setIsIndent(false);
				}
			}
		}
	}

	public Set<String> names() {
		return this.keySet();
	}
}
