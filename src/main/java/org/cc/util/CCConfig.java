package org.cc.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.cc.CC;
import org.cc.CCMap;
import org.cc.ICCMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author william
 * 只能取到值不能變更值
 */

public class CCConfig extends CCMap {
    
    protected Logger log = LoggerFactory.getLogger(CCConfig.class);


    public CCConfig() {
        this(null);
    }
	
	public CCConfig(String dv) {
        this(dv,"cfg.json");  // $base/cfg.json		
    }

    // $base/file_name 
    public CCConfig(String dv, String file_name) {
        super();
        base(dv); // setting base
        init(file_name);
    }

    protected void init(String file_name){
		File cfg_path = new File(base(),file_name);
		log.debug("===== cfg_path : "+cfg_path);
        ICCMap m = new  CCJSONParser( cfg_path,"UTF-8").parser_obj();
        if(m instanceof Map){
          this.putAll((Map<? extends String,?>) m);
        }
    }


    private String base(String dv) {
        if (!has("$base")) {
            String base = System.getProperty("base", dv);
            base = (base != null) ? base : ".";
            try {
                base = new File(base).getCanonicalPath();
                this.put("$base", base);
            } catch (IOException ex) {
                log.error("Can't find : " + base);
            }
        }
        return this.str("$base");
    }
    
    public String base(){
        return base(null);
    }

	public String path(String prefix){
		return base()+"/" + this.str(prefix,"");
	}
	
    public  void load(String id){
        ICCMap m = CC.load(new File(base(), id + ".json"),"UTF-8");
        if(m!=null){
            put(id,m);
        }
    }

	/**
	 *
	 * @param id
	 * @return
	 */
	@Override
    public ICCMap map(String id){
        if(has(id)){
            Object o = get(id);
            if(o instanceof String){
                File f = new File(base(),((String)o)+".json");
                if(f.exists()){
                    ICCMap m =  CC.load(f,"UTF-8");
                    put(id,m);
                    return m ;
                }
            } else if ( o instanceof ICCMap) {
                return (ICCMap) o ;
            }
        }
        throw new RuntimeException("Can't get ICCMap object : " + id);
    }

	public File file(String prefix , String id ){
		String path = path(prefix);
		return new File(path,id+".json");
	}

}
