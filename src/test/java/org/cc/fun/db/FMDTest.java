package org.cc.fun.db;


import org.cc.CCConst;
import org.cc.model.CCFunc;
import org.cc.model.CCProcObject;
import org.junit.Test;

/**
 *
 * @author william
 */
public class FMDTest {

    @Test
    public void test_jometadata() throws Exception {
       // String base = CCConst.base("stock");
       String base = CCConst.base("baphiq");
       System.out.println(base);
        
        try (CCProcObject proc = new CCProcObject(base,"db");) {                 
            System.out.println("產出meta檔");
            //String metaId = "psSaleForeign";
            //String metaId = "psBaphiqE1Info";
            //String metaId = "rawMaterialManufacture";
            //String metaId = "rawMaterialImport";
            String metaId = "psFixMain";
            proc.params().put("table", metaId);
            System.out.println("-----------------------------------------------------");
            System.out.println(proc.params());
            System.out.println("-----------------------------------------------------");
            CCFunc.apply("db.proc_metadata", proc);
           // System.out.println(new UMLClass(proc.base(), metaId).toERDString());
            //GDocDao.gen_schema(metaId);
        } 
    }
    
}
