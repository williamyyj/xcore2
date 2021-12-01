package org.cc.fun.db;

import java.util.List;
import java.util.function.Function;
import org.cc.model.CCField;


/**
 *   
 * @author william
 */
public class FSQLUpdate extends FSQLBase implements Function<List<CCField>, String> {

    @Override
    public String apply(List<CCField> fields) {
        CCField tb = fields.get(0);
        if (!"table".equals(tb.dt())) {
            throw new RuntimeException("CCField must tb field : " + tb);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tb.name()).append(" set");
        proc_update_cols(sql, fields,"P");
        sql.append("\nwhere 1=1 ");
        proc_cols_cond(sql, fields,"P");
        return sql.toString();
    }



 
}
