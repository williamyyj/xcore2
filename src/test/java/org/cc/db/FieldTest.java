package org.cc.db;


import org.cc.CCTest;
import org.cc.model.CCField;
import org.cc.model.CCProcObject;
import org.junit.Test;

public class FieldTest {

    @Test
    public void testDBCmdField() {
        String base = CCTest.project + "\\baphiq";

        try (CCProcObject proc = new CCProcObject(base,false)) {
            proc.params().put("xxxx",100);
            DBCmdField qfld = new DBCmdField(proc, "=,a,int,xxxx");
            System.out.println(qfld.toString());
            System.out.println("---------------------");
            System.out.println(qfld.getValue().getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_ccfield(){
        CCField fld = new CCField("{id:xxx,dt:string,label:測試欄位,name:yyyy,ct:p}");
        System.out.println(fld.toString(4));
    }

    @Test
    public void test_loop(){
        long ts = System.nanoTime() ;
        double x = 0.0;
        for(int i=0;i<1000000;i++){
            x +=i ;    
        }
        long te = System.nanoTime();
        System.out.println("ret:"+x);
        System.out.println("time:"+(te-ts)/1E9);

    }

}

