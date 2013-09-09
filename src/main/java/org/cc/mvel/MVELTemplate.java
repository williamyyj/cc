package org.cc.mvel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

public class MVELTemplate implements ITemplate {
	
	File src ; 
	long lastModified ; 
	CompiledTemplate template ; 
	
	public MVELTemplate(File src ){
		this.src = src ; 
	}

	public String processTemplate(Map<String, Object> m) throws Exception {
		reload();
		String context = null;
		if(template!=null){
			m.put("$self", m);
			m.put("$MVEL", MVEL.class);
			context = (String) TemplateRuntime.execute(template,m);
		}
		return context ; 
	}
	
	public void reload(){
		if(src.lastModified()>this.lastModified){
			try {
				template = TemplateCompiler.compileTemplate(loadString(src,"UTF-8"));
				lastModified = src.lastModified();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String loadString(File src, String enc) throws Exception{
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src),"UTF-8"));
		String line = null ; 
		while((line=br.readLine())!=null){
			sb.append(line).append("\r\n");
		}
		br.close();
		return sb.toString();
	}

}
