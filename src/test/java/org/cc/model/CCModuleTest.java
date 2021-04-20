package org.cc.model;

import org.cc.CCTest;
import org.cc.ICCField;
import org.junit.Test;

public class CCModuleTest {
    
    @Test
    public void test_loadMetadata(){
        String base = CCTest.project+"/stock";
        try( CCProcObject proc = new CCProcObject(base);){
            CCModulePrjMode m = new CCModulePrjMode(proc);
            CCMetadata md = new CCMetadata(m,"mstock");
            for(ICCField fld :md.dbFields()){
                System.out.println(fld);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("---------------------------------");
    }
}
