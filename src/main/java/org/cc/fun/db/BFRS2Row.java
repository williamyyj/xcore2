package org.cc.fun.db;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiFunction;
import org.cc.ICCType;
import org.cc.json.JSONObject;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author william
 */
@Log4j2
public class BFRS2Row implements BiFunction<List<JSONObject>, ResultSet, JSONObject> {

    @Override
    public JSONObject apply(List<JSONObject> metadata, ResultSet rs) {
        JSONObject row = new JSONObject();
        for(JSONObject meta : metadata){
            try {
                ICCType<?> type = (ICCType<?>) meta.get("type");
                String name = meta.optString("name");
                row.put(name, type.getRS(rs, name));
            } catch (SQLException ex) {
                log.error("fail : "+meta.toString(), ex);
            }
        }
        return row;
    }

}
