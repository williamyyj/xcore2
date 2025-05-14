package org.cc.fun.proc.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.cc.db.DBCmd;
import org.cc.db.DBCmdField;
import org.cc.fun.db.FSQLInsert;
import org.cc.json.JSONObject;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;

/**
 * 提供 bacth insert , update
 */
public class BiDaoBacthInsert extends BiDaoBase {

    protected FSQLInsert fsql = new FSQLInsert();
    private CCProcObject proc;
    private List<JSONObject> data; // List<JSONObject>
    private String mid;
    private String actId;
    private int[] ret;
    private PreparedStatement ps;
    private DBCmd cmd;


    public BiDaoBacthInsert(CCProcObject proc, List<JSONObject> data, String mid, String actId) {
        this.proc = proc;
        this.data = data;
        this.mid = mid;
        this.actId = actId;
    }

    public int[] executeBatch() {
        try {
            proc_bacth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private void proc_bacth() throws Exception {
        proc.db().connection().setAutoCommit(false); // 如果要記前次有點不合理表示非完整批次作業
        try {
            proc_batch_begin();
            proc_batch_each();
            proc.db().connection().commit();
        } catch (Exception e) {
            e.printStackTrace();
            proc.db().connection().rollback();
        } finally {
            if (ps != null) {
                ps.close();
            }
            proc.db().connection().setAutoCommit(true); // 回到real time 作業
        }
    }

    private void proc_batch_each() throws SQLException {
        for (JSONObject item : data) { // for this version JSONArray support
            try {
                proc_item(item);
            } catch (SQLException se) {
                se.printStackTrace();
                System.out.println(item);
            }
        }
        ret = ps.executeBatch(); // 排除異常匯入
    }

    private void proc_item(JSONObject item) throws SQLException {
        int idx = 1;
        for (DBCmdField fld : cmd.qrFileds()) {
            fld.setValue(getFieldValue(fld, item));
            fld.getType().setPS(ps, idx, fld.getValue());
            idx++;
        }
        ps.addBatch();
    }

    private void proc_batch_begin() throws SQLException {
        ICCModule md = proc.module(mid);
        String sql = fsql.apply(md.dbFields(actId));
        proc.put("$", data.get(0)); // Get first item setting sql pattern
        cmd = new DBCmd(proc, sql);
        ps = proc.db().connection().prepareStatement(cmd.sqlString());
    }

    private Object getFieldValue(DBCmdField fld, JSONObject p) {
        Object value = null;
        if (p.has(fld.getId())) {
            value = fld.getType().value(p.get(fld.getId()));
        }
        if (value == null && fld.getAlias() != null && p.has(fld.getAlias())) {
            value = fld.getType().value(p.get(fld.getAlias()));
        }
        return value;
    }


}
