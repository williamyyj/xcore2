package org.cc.fun.db;

import java.io.File;
import java.sql.ResultSet;
import java.util.Set;
import java.util.function.Function;
import org.cc.CCConst;
import org.cc.data.CCData;
import org.cc.json.CCJSON;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;
import org.cc.model.CCFunc;
import org.cc.model.CCProcObject;


/**
 * @author william
 */
public class proc_metadata extends proc_base implements Function<CCProcObject, Boolean> {

    @Override
    public Boolean apply(CCProcObject proc) {
        JSONObject p = proc.params();
        String table = p.optString("table", null);
        if (table == null) {
            throw new RuntimeException("Can't find proc.params.table ");
        } else {
            try {
                JSONObject nm = exec(proc, table);
                proc_mix(new File(proc.base() + "/module/$meta", table + ".json"), nm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public JSONObject exec(CCProcObject proc, String table) throws Exception {
        JSONObject nm = new JSONObject();
        JSONArray ncols = new JSONArray();
        ResultSet rs = null;
        StringBuilder name = new StringBuilder();
        name.append(table).append(',');
        ncols.put(fldTable(table));
        try {
            Set<String> pk =  CCFunc.apply("db.procmd_pk", proc);
            rs = dbmd(proc).getColumns(catalog(proc), schema(proc), table, null);
            while (rs.next()) {
                JSONObject col = col(proc, rs);
                if (pk.contains(col.optString("name"))) {
                    col.put("ct", "P");
                }
                name.append(col.optString("id")).append(',');
                ncols.put(col);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        name.setLength(name.length() - 1);
        nm.put(CCConst.meta_fields, name);
        nm.put("id", table);
        nm.put("name", table);
        nm.put("meta", ncols);
        return nm;
    }

    private boolean get_is_null(ResultSet rs) throws Exception {
        String is_null = rs.getString("IS_NULLABLE");
        return ("Y".equalsIgnoreCase(is_null) || "YES".equalsIgnoreCase(is_null));
    }

    private JSONObject col(CCProcObject proc, ResultSet rs) throws Exception {
        JSONObject col = new JSONObject();
        col.put("__indent__", false);
        String name = rs.getString("COLUMN_NAME");
        col.put("name", name);
        col.put("id", proc.db().to_alias(name));
        int dt = rs.getInt("DATA_TYPE");
        col.put("dt", proc.db().types().type(dt).dt());
        col.put("jdbc", rs.getString("TYPE_NAME"));
        col.put("size", rs.getInt("COLUMN_SIZE"));
        col.put("note", "");
        col.put("label", "");
        if (!get_is_null(rs)) {
            col.put("ct", "M");
        }
        check_auto(col);
        //col.remove("jdbc");//  
        return col;
    }

    private void check_auto(JSONObject col) {
        String jdbc = col.optString("jdbc");
        if (jdbc.contains("identity")) {
            col.put("ft", "auto");
        }
    }

    private void proc_mix(File f, JSONObject nm) throws  Exception {
        JSONObject ret = nm;
        if (f.exists()) { // 如果有舊檔         
            JSONObject om = CCJSON.load(f, "UTF-8");
            JSONArray ocols = (JSONArray) om.remove("meta");
            JSONArray ncols = (JSONArray) nm.remove("meta");
            JSONArray rcols = new JSONArray();
            String tbFields = nm.optString("$tbFields"); //
            ret = CCJSON.mix(nm, om);
            ret.put("$tbFields", tbFields);
            for (int i = 0; i < ncols.length(); i++) { // 調整新DB Schema  
                JSONObject nrow = ncols.optJSONObject(i);
                JSONObject orow = CCJSON.get(ocols, "id", nrow.optString("id"));
                JSONObject row = CCJSON.mix(nrow, orow);
                if (orow != null) {//  人工調整
                    row.put("label", orow.get("label"));
                    row.put("note", orow.get("note"));
                    row.put("ct", orow.get("ct"));
                }
                rcols.put(row);
            }

            for (int i = 0; i < ocols.length(); i++) { // 合併舊項
                JSONObject orow = ocols.optJSONObject(i);
                JSONObject nrow = CCJSON.get(ncols, "id", orow.optString("id"));
                if (nrow == null) {
                    orow.put("__indent__", false);
                    rcols.put(orow);
                }
            }
            ret.put("meta", rcols);
        }
        CCData.saveText(f, ret.toString(4),"UTF-8");
    }

    private JSONObject fldTable(String table) {
        JSONObject ret = new JSONObject();
        ret.put("__indent__", false);
        ret.put("id", table);
        ret.put("name", table);
        ret.put("dt", "table");
        return ret;
    }

}
