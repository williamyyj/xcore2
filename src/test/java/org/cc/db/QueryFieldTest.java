package org.cc.db;

import org.cc.CCTest;
import org.cc.model.CCProcObject;
import org.junit.Test;

public class QueryFieldTest {

    @Test
    public void testQueryField() {
        String base = CCTest.project + "\\baphiq";

        try (CCProcObject proc = new CCProcObject(base, CCProcObject.prjPrefix,"db")) {
            proc.params().put("xxxx",100);
            QueryField qfld = new QueryField(proc, "=,a,int,xxxx");
            System.out.println(qfld.toString());
            System.out.println("---------------------");
            System.out.println(qfld.getValue().getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

