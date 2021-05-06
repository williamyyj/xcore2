package org.cc.model;

import org.cc.CCTest;
import org.cc.json.JSONObject;
import org.junit.Test;

public class CCSqlLiteTest {

    @Test
    public void test_insert() {
        String base = CCTest.project + "/stock";
        try (CCProcObject proc = new CCProcObject(base, false)){
           JSONObject jo = new JSONObject("{stockid:9901,sdate:'2021-05-04 13:05:01',so:100,sh:102,sl:99,sc:101}");
           proc.put("$",jo);
           CCProcUtils.exec(proc, "dao_add@twse,mstock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test_xxxx() {
        System.out.println("ok!!!!!!!X老家!XXaa");
    }
}
