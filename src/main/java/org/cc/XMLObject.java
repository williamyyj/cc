package org.cc;

public class XMLObject extends CCMap {
       
    public XMLObject(String tag){
        super();
        put("$tag",tag);
    }

    private XMLObject(XMLObject old) {
       super(old.model());
    }
    public void add_child(String name, Object o){
       Object child =  get(name);
       if(child == null){
           put(name,o);
       } else if( child instanceof ICCList){
           ((ICCList)child).set(o);
       } else if (child instanceof ICCMap){
           XMLObject c = (XMLObject) child ; 
           ICCList list = new CCList();
           list.set(c);
           list.set(o);
           put(name, list);
       }
    }
    
    public String tag(){ return str("$tag"); } 
    

		
		
    public Object normal(){
        XMLObject old = new XMLObject(this);
        Object tag = old.remove("$tag");
        if(old.size()==1 && old.has("$text")) return old.str("$text");
        if(old.size()==1 && old.has("$cdata")) return old.str("$cdata");    
        old.put("$tag", tag);
        return old;
    }
    
}
