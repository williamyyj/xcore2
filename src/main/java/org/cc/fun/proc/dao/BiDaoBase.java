package org.cc.fun.proc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.cc.db.DBCmd;
import org.cc.db.DBCmdField;
import org.cc.db.ICCDB;
import org.cc.model.CCProcObject;

public class BiDaoBase {

    public long insert(CCProcObject proc, DBCmd cmd) {
        // 給Insert使用回傳PK用
        ICCDB db = proc.db();
        try (PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())) {
            fillStatment(ps, cmd.qrFileds());
            ps.execute();
            if (ps.getMoreResults()) {
                try (ResultSet rs = ps.getResultSet();) {
                    if (rs != null && rs.next()) {
                        return rs.getLong(1);
                    }
                } 
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public boolean executeUpdate(CCProcObject proc, DBCmd cmd) {
        // 標準DML使用
        ICCDB db = proc.db();
        try (PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())) {

        } catch (Exception e) {

        }
        return false;
    }

    public boolean executeQuery(CCProcObject proc, DBCmd cmd ){
        ICCDB db = proc.db();
        try(PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())){
   
        } catch (Exception e) {
            
        }
        return false;
    }

    public void fillStatment(PreparedStatement ps, List<DBCmdField> qrFields) throws SQLException {
        int idx = 1;
        for (DBCmdField fld : qrFields) {
            fld.getType().setPS(ps, idx, fld.getValue());
            idx++;
        }

    }


}
