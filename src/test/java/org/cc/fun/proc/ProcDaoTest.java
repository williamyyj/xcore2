package org.cc.fun.proc;

import org.cc.AppStockTest;
import org.cc.json.CCJSON;
import org.cc.json.JSONObject;
import org.cc.model.CCProcObject;
import org.cc.model.CCProcUtils;
import org.junit.Test;

public class ProcDaoTest {

    @Test
    public void test_pk(){
        String base = AppStockTest.base;
        try (CCProcObject proc = new CCProcObject(base, false)){
            JSONObject row = CCJSON.line("{stockid:2330,sdate:'Wed Dec 14 00:00:00 CST 2005'}");
            proc.put("$",row);
            JSONObject old = (JSONObject) CCProcUtils.exec(proc, "proc.dao.BiDaoPKRow@twse,mstock");
            System.out.println(old);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
