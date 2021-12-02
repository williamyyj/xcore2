package org.cc.fun.db;

import java.util.List;
import org.cc.model.CCField;

public class FSQLBase {

    /**
     * select * from ${table} where 1=1 ${=,a,..} ${=,a,..} ...
     * 
     * @param sql
     * @param fields
     * @param ct
     */
    protected void proc_cols_cond(StringBuilder sql, List<CCField> fields, String ct) {
        for (CCField fld : fields) {
            if (fld.ct().contains(ct)) {
                sql.append("${=,").append(fld.name()).append(',').append(fld.dt()).append(',')
                        .append(fld.id()).append("}");
            }
        }
    }

    /**
     *  select * from ${table} where a=${} and b=${} ...
     * 
     * @param sql
     * @param fields
     * @param ct
     */
    protected void proc_key_cond(StringBuilder sql, List<CCField> fields, String ct) {
        sql.append(" where");
        for (CCField fld : fields) {
            if (fld.ct().contains(ct)) {
                sql.append(" ").append(fld.name()).append(" = ").append("${").append(fld.name()).append(',')
                        .append(fld.dt()).append(',').append(fld.id()).append("} and");
            }
        }
        if (fields.size() > 0) {
            sql.setLength(sql.length() - 3); // remover and
        }
    }



    /**
     * pk uk 不能變更 ${$set,mem_passwd,string,mempasswd}
     * 
     * @param sql
     * @param fields
     * @param ct
     */
    protected void proc_update_cols(StringBuilder sql, List<CCField> fields, String ct) {
        for (CCField fld : fields) {
            if (!fld.ct().contains(ct) && !"table".equals(fld.dt())) {
                sql.append('\n').append("${$set").append(',').append(fld.name()).append(',')
                        .append(fld.dt()).append(',').append(fld.id()).append('}');
            }
        }
        sql.append("${$rm}");
    }

}
