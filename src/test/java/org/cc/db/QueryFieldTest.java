package org.cc.db;

import org.junit.Test;

public class QueryFieldTest {

    @Test
    public void testQueryField() {
        QueryField qfld = new QueryField(null, "");
        qfld.setId("a");
        qfld.setDt("string");
        System.out.println(qfld.toString());
    }

}
