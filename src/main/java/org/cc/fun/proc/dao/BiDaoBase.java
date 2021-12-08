package org.cc.fun.proc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.cc.db.DBCmd;
import org.cc.db.ICCDB;
import org.cc.fun.db.BCCmdFill;
import org.cc.fun.db.BFRS2Row;
import org.cc.fun.db.BFRS2Rows;
import org.cc.fun.db.BFRSMetadata;
import org.cc.json.JSONObject;
import org.cc.model.CCField;
import org.cc.model.CCProcObject;

public class BiDaoBase {

    protected BCCmdFill cmdFill = new BCCmdFill();
    protected BFRSMetadata biRS2meta = new BFRSMetadata();
    protected BFRS2Row biRS2row = new BFRS2Row();
    protected BFRS2Rows biRS2rows = new BFRS2Rows();

    public JSONObject row(CCProcObject proc, DBCmd cmd){
        ICCDB db = proc.db();
        try (PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())) {
            cmdFill.accept(ps, cmd.qrFileds());
            try( ResultSet rs = ps.executeQuery();){
                List<CCField> rsFields = biRS2meta.apply(db.types(), rs);
                if(rs.next()){
                    return biRS2row.apply(rsFields, rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject(); // 減少防呆檢查
    }

    public List<JSONObject> rows(CCProcObject proc, DBCmd cmd){
        ICCDB db = proc.db();
        try (PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())) {
            cmdFill.accept(ps, cmd.qrFileds());
            try( ResultSet rs = ps.executeQuery();){
                List<CCField> rsFields = biRS2meta.apply(db.types(), rs);
                return biRS2rows.apply(rsFields, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<JSONObject>(); // 減少防呆檢查
    }

    /**
     * 這個目前符合MSSQL
     * @param proc
     * @param cmd
     * @return
     */
    public int insert(CCProcObject proc, DBCmd cmd) {
        // 給Insert使用回傳PK用
        ICCDB db = proc.db();
        try (PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())) {
            cmdFill.accept(ps, cmd.qrFileds());
            ps.execute();
            if (ps.getMoreResults()) {
                try (ResultSet rs = ps.getResultSet();) {
                    if (rs != null && rs.next()) {
                        return rs.getInt(1);
                    }
                } 
            } else {
                return 1; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int executeUpdate(CCProcObject proc, DBCmd cmd) {
        // 標準DML使用
        ICCDB db = proc.db();
        try (PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())) {
            cmdFill.accept(ps, cmd.qrFileds());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }



  

 


}
