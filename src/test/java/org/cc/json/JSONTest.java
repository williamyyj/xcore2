package org.cc.json;

import org.junit.Test;

public class JSONTest {
    @Test
    public void test_ja(){
        JSONArray ja = new JSONArray("[1,2,3,4,5]");
        if(ja!=null){
            for(Object o : ja){
                System.out.println(o);
            }
        }
    }

    @Test
    public void test_jo_01(){
        JSONObject jo = new JSONObject("{a:1,'b':2,'c':'ok item'}");
        System.out.println(jo);
    }
}
