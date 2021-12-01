package org.cc.fun.db;

import java.util.List;
import java.util.function.Function;
import org.cc.model.CCField;


/**
 *   
 * @author william
 */
public class FSQLUUpdate extends FSQLBase implements Function<List<CCField>, String> {

    @Override
    public String apply(List<CCField> fields) {
        CCField tb = fields.get(0);
        if (!"table".equals(tb.dt())) {
            throw new RuntimeException("CCField must tb field : " + tb);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tb.name()).append(" set");
        proc_update_cols(sql, fields,"U");
        sql.append("\nwhere 1=1 ");
        proc_cols_cond(sql, fields,"U");
        return sql.toString();
    }

   

}
