package org.cc.db;

import org.cc.CCMap;
import org.cc.ICCMap;
import org.cc.fun.db.SQLFInsert;
import org.cc.meta.MetaTable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/8/12
 * Time: 下午 3:57
 * To change this template use File | Settings | File Templates.
 */
public class CSVImport {

    public static void main(String[] args) throws Exception {
        DBSchema meta = new DBSchema("prj/baphiq");
        CSVImport ci = new CSVImport();

        try{
            MetaTable mt = new MetaTable(meta,"Crop_T");
            List<ICCMap> rows = ci.to_map(new File("prj/baphiq/data/crop_t.csv"),"UTF-8");
            SQLFInsert fins = new SQLFInsert();
            for(ICCMap row : rows){
                String sql = fins.exec(mt,row) ;
                System.out.println(sql+";");
            }
        } finally {
            meta.close();
        }
    }

    public List<ICCMap> to_map(File f, String enc) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), enc));
        List<ICCMap> list = new ArrayList<ICCMap>();
        try {
            String line = null;
            int idx = 0 ;
            String[] heads = null ;
            while ((line = br.readLine()) != null) {
                idx++;
                if(idx==1){
                    heads = line.split(",");
                    continue;
                }
                String items[] = line.split(",");
                ICCMap m = new CCMap();
                for(int i=0; i<heads.length ; i++){
                    m.set(heads[i],items[i]);
                }
                list.add(m);
            }
        } finally {
            br.close();
        }
        return list;
    }
}
