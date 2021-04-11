package org.cc.fun.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import org.cc.ICCType;
import org.cc.json.JSONObject;
import org.cc.type.CCTypes;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author william
 */

 @Log4j2
public class BFRSMetadata implements BiFunction<CCTypes, ResultSet, List<JSONObject>> {

    @Override
    public List<JSONObject> apply(CCTypes types, ResultSet rs) {
        List<JSONObject> metadata = new  ArrayList<JSONObject>();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int len = rsmd.getColumnCount();
            for (int i = 0; i < len; i++) {
                int idx = i + 1;
                JSONObject meta = new JSONObject();
                String name = rsmd.getColumnName(idx);
                int dt = rsmd.getColumnType(idx);
                ICCType<?> type = types.type(dt);
                meta.put("name", name);
                meta.put("dt", type.dt());
                meta.put("type", type);
                metadata.add(meta);
            }
        } catch (SQLException ex) {
            log.error(ex);
        }
        return metadata;
    }
}
