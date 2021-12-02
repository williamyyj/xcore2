package org.cc.fun.db;

import java.util.List;
import java.util.function.Function;
import org.cc.model.CCField;

public class FSQLPK extends FSQLBase implements Function<List<CCField>,String> {
    @Override
    public String apply(List<CCField> flds) {
        CCField tb = flds.get(0);
        if (!"table".equals(tb.dt())) {
            throw new RuntimeException("JOField must tb field : " + tb);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(tb.name());
        proc_key_cond(sql, flds, "P");
        return sql.toString();
    }
    
 
}
