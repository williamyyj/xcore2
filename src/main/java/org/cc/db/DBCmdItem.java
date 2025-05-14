package org.cc.db;

import java.util.HashMap;
import java.util.Map;
import org.cc.CCConst;
import org.cc.ICCType;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;


/**
 * @author William 動態SQL用
 *
 */
public class DBCmdItem {

    private static Map<String, String> op;

    private static Map<String, String> op() {
        if (op == null) {
            op = new HashMap<String, String>();
            op.put("=", "=");
            op.put(">", ">");
            op.put(">=", ">=");
            op.put("<", "<");
            op.put("<=", "<=");
            op.put("$like", "like"); //   xxx%
            op.put("$all", "like");   //   %xxxx%
            op.put("$range", "");  //     fld  betten a and b 
            op.put("$set", "="); // for update 
            op.put("$rm", "");  // 移除最後 ","   
            op.put("@", "");  //    for table query or const 
            op.put("$expr", "$expr");
        }
        return op;
    }

    public static String get_command(JSONObject meta, String id) {
        Object o = meta.get(id);
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof JSONArray) {
            JSONArray arr = meta.optJSONArray(id);
            StringBuilder sb = new StringBuilder();
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    sb.append(arr.get(i));
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static void process_item(ICCDB db, StringBuffer sb, JSONObject mq, JSONObject row, String item) throws Exception {
        String line = item.trim();
        String[] args = null;
        if (line.charAt(0) == '[') {
            JSONArray arr = new JSONArray(line);
            args = arr.asList().toArray(new String[0]);
        } else {
            args = item.split(",");
        }
        String name = args[0];
        if (name.charAt(0) == '@') {
            process_const(db, sb, mq, row, args);
        } else if (op().containsKey(name)) {
            process_op_item(db, sb, mq, row, args);
        } else if (item.startsWith("expr")) {
            process_expr(db, sb, mq, row, item);
        } else {
            process_var_item(db, sb, mq, row, args);
        }

    }

    private static void process_op_item(ICCDB db, StringBuffer sb, JSONObject mq, JSONObject row, String[] args) {
        String name = args[0];
        if ("=".equals(name) || ">".equals(name) || ">=".equals(name)
          || "<".equals(name) || "<=".equals(name)) {
            process_op2(db, sb, mq, row, args);
        } else if ("$like".equals(name)) {
            process_like(db, sb, mq, row, args);
        } else if ("$all".equals(name)) {
            process_all(db, sb, mq, row, args);
        } else if ("$range".equals(name)) {
            process_range(db, sb, mq, row, args);
        } else if ("$set".equals(name)) {
            process_set(db, sb, mq, row, args);
        } else if ("$rm".equals(name)) {
            process_rm(db, sb, mq, row, args);
        }
    }

    private static void set_field(JSONArray fields, ICCDB db, JSONObject row, String name, String dt, String id, Object v) {
        JSONObject fld = new JSONObject();
        fld.put("name", name);
        fld.put("id", id);
        fld.put("dt", dt);
        fld.put("value", v);
        fld.put("type", db.types().type(dt));
        fields.put(fld);
    }

    private static void process_var_item(ICCDB db, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        //    ${field,dt}  |   ${field,dt,alias} 
        //System.out.println(Arrays.toString(args));
        String name = args[0];
        String dt = args[1];
        String alias = (args.length > 2) ? args[2] : null;
        JSONArray fields = model.optJSONArray(CCConst.p_fields);
        Object v = get_value(db, row, name, dt, alias);
        set_field(fields, db, row, name, dt, alias, v);
        sb.append("?");
    }

    private static Object get_value(ICCDB db, JSONObject row, String name, String dt, String alias) {
        //System.out.println("===== debug row " + row);
        //System.out.println("===== debug name " + name);
        //System.out.println("===== debug dt " + dt);
        //System.out.println("===== debug alias " + alias);

        ICCType<?> type = db.types().type(dt);
        //System.out.println("===== debug type " + type);
        Object value = null;
        if (row.has(name)) {
            value = type.value(row.get(name));
        }
        if (value == null && alias != null && row.has(alias)) {
            //Object av = row.get(alias);
            //System.out.println("===== debug av " + av);
            value = type.value(row.get(alias));
        }

        return value;
    }

    private static void process_op2(ICCDB db, StringBuffer sb, JSONObject mq, JSONObject row, String[] args) {
        String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        Object v = get_value(db, row, field, dt, alias);
        if (v != null) {
            JSONArray fields = mq.optJSONArray(CCConst.p_fields);
            set_field(fields, db, row, field, dt, alias, v);
            sb.append(" and ").append(field).append(' ').append(op_name).append(" ?");
        }
    }

    private static void process_expr(ICCDB db, StringBuffer sb, JSONObject mq, JSONObject row, String item) {
        String[] args = item.split(":");
        //String op_name = args[0];
        String name = args[1];
        String dt = args[2];
        String expr = args[3];
        Object v = get_value(db, row, name, dt, name);
        if (v != null) {
            JSONArray fields = mq.optJSONArray(CCConst.p_fields);
            set_field(fields, db, row, name, dt, name, v);
            sb.append(" and ").append(expr);
        }
    }

    private static void process_like(ICCDB db, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        //String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        Object v = get_value(db, row, field, dt, alias);
        if (v != null) {
            JSONArray fields = model.optJSONArray(CCConst.p_fields);
            set_field(fields, db, row, field, dt, alias, v + "%");
            sb.append(" and ").append(field).append(" like ").append(" ?");
        }
    }

    private static void process_all(ICCDB db, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        //String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        Object v = get_value(db, row, field, dt, alias);
        if (v != null) {
            JSONArray fields = model.optJSONArray(CCConst.p_fields);
            set_field(fields, db, row, field, dt, alias, "%" + v + "%");
            sb.append(" and ").append(field).append(" like ").append(" ?");
        }
    }

    private static void process_range(ICCDB db, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
       // String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        JSONArray fields = model.optJSONArray(CCConst.p_fields);
        Object v1 = get_value(db, row, field + "_1", dt, alias + "_1");
        set_field(fields, db, row, field + "_1", dt, alias + "_1", v1);
        Object v2 = get_value(db, row, field + "_2", dt, alias + "_2");
        set_field(fields, db, row, field + "_2", dt, alias + "_2", v2);
        sb.append(" and").append(field).append(" beteen ? and ?");
    }

    private static void process_set(ICCDB db, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
       // String op_name = args[0]; // set 
        String field = args[1]; // 
        String dt = args[2]; // 
        String alias = (args.length > 3) ? args[3] : null;
        JSONArray fields = model.optJSONArray(CCConst.p_fields);
        Object v = get_value(db, row, field, dt, alias);
        if (v != null) {
            set_field(fields, db, row, field, dt, alias, v);
            sb.append(' ').append(field).append(" = ").append(" ? ,");
        }
    }

    private static void process_rm(ICCDB db, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        int ps = sb.lastIndexOf(",");
        if (ps > 0) {
            sb.setLength(ps - 1);
        }
    }

    private static void process_const(ICCDB db, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        // 這是必填 or 有預設值
        String id = args[0];
        String v = row.optString(id, null);
        if (v != null) {
            sb.append(v);
        }
    }

}
