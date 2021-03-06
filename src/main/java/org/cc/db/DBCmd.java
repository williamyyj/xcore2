package org.cc.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cc.json.JSONArray;
import org.cc.model.CCProcObject;

import lombok.ToString;

/**
 * 查詢使用物件 CCProcUtils.exec(proc,"$in>db.rows@metaId,actId>$out");
 */
@ToString
public class DBCmd {

    private final static Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");

    private List<DBCmdField> qrFields = new ArrayList<>();

    private StringBuffer sql = new StringBuffer();

    public DBCmd(CCProcObject proc, Object cmd) {
        String sqlString = getSqlString(cmd);
        parser_cmd(proc, sqlString);
    }



    private String getSqlString(Object cmd) {
        if(cmd instanceof String){
            return (String) cmd ;
        } else if (cmd instanceof JSONArray){
            StringBuffer buf = new StringBuffer();
            JSONArray ja = (JSONArray) cmd;
            for(Object o : ja ){
                buf.append(o).append(" ");
            }
            return buf.toString();
        }
        return "";
    }



    private void parser_cmd(CCProcObject proc, String cmd) {
        sql.setLength(0);
        Stack<StringBuffer> stack = new Stack<StringBuffer>();
        Matcher match = p.matcher(cmd);
        while (match.find()) {
            String item = match.group(1);
            match.appendReplacement(sql, ""); // 直接清空
            if ("or".equals(item)) { // begin ${or} ..... ${end}
                stack.push(sql);
                sql = new StringBuffer();
            } else if ("end".equals(item)) {
                if (sql.length() > 0) {
                    String child = sql.toString().replaceFirst("and", "");
                    child = child.replaceAll("and", "or");
                    sql = stack.pop();
                    sql.append(" and (").append(child).append(" )");
                } else {
                    sql = stack.pop();
                }
            } else if (!item.startsWith("rem")) {
                DBCmdField qf = new DBCmdField(proc, item);
                process_item(qf);
            }
        }
        match.appendTail(sql);
    }

    private void process_item(DBCmdField qf) {
        if(qf.getOp()==null){
            sql.append(" ? ");
            qrFields.add(qf);
        } else {
            qf.getOp().toCommand(qrFields, sql, qf);
        }  
    }

   
    public List<DBCmdField> qrFileds() {
        return qrFields;
    }

    public String sqlString() {
        return sql.toString();
    }

    public StringBuffer sql(){
        return sql;
    }

}
