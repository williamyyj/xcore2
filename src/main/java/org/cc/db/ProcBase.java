package org.cc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.cc.ICCType;
import org.cc.fun.db.BCPSFill;
import org.cc.fun.db.BFRS2Row;
import org.cc.fun.db.BFRS2Rows;
import org.cc.fun.db.BFRSMetadata;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;
import org.cc.type.CCTypes;

import lombok.extern.log4j.Log4j2;


/**
 *
 * @author william
 */
@Log4j2
public class ProcBase {

    protected BiConsumer<PreparedStatement, Object[]> ps_fill = new BCPSFill();
    protected BiFunction<List<JSONObject> , ResultSet, JSONObject> rs_row = new BFRS2Row();
    protected BiFunction<List<JSONObject> , ResultSet, List<JSONObject>> rs_rows = new BFRS2Rows();
    protected BiFunction<CCTypes, ResultSet, List<JSONObject> > rs_metadata = new BFRSMetadata();
 
    protected void proc_fill(ICCDB db, PreparedStatement ps, JSONArray fields) throws SQLException {
        int index = 0;
        for(Object o : fields){ 
            JSONObject fld = (JSONObject) o;
            String dt = fld.optString("dt");
            Object value = fld.get("value");
            ICCType<?> type = db.types().type(dt);
            type.setPS(ps, index + 1, value); 
            index++;
        }
    }
    
    


    public void __release(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }

    public void __release(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }

    public void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }

}
