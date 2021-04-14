package org.cc.fun.db;

import org.cc.db.ProcBase;
import org.cc.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;
import org.cc.CCConst;
import org.cc.model.CCProcObject;



/**
 *
 * @author william
 */
public class proc_rows extends ProcBase implements Function<CCProcObject, List<JSONObject>> {

    @Override
    public List<JSONObject> apply(CCProcObject proc) {
        JSONObject p = proc.params();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = p.optString(CCConst.p_sql);
        try {
            ps = proc.db().connection().prepareStatement(sql);
            if (p.containsKey(CCConst.p_fields)) {
                this.proc_fill(proc.db(), ps, p.optJSONArray(CCConst.p_fields));
            } else {
                ps_fill.accept(ps, (Object[]) p.get(CCConst.p_params));
            }
            rs = ps.executeQuery();
            ICCList mFields = rs_metadata.apply(proc.db().types(), rs);
            return rs_rows.apply(mFields, rs);
        } catch(Exception e){
        } finally {
            __release(rs);
            __release(ps);
        }
        return null;
    }

}
