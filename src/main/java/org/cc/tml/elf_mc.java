package org.cc.tml;

import java.io.File;
import java.util.Map;
import org.cc.CC;
import org.cc.ICCMap;


/**
 *
 * @author william
 */
public class elf_mc implements ICCVMFunction {

	@Override
	public void exec(ICCVM vm, Map<String, Object> m, Object... args) throws Exception {
		String fid = (String) args[1];
		String gid = (String) args[2];
		File f = new File(vm.base(),fid+".json");
		//System.out.println("===== path : " + f);
		ICCMap xo = CC.load(f,"UTF-8");	
		ICCMap mp = xo.map(gid);
		if(mp!=null){
			String key = (String) vm.eval(m, args[3]);
			Object ref = mp.obj(key);
			if(ref != null){
				vm.print(ref);
			}
		}		
	}

	
}
