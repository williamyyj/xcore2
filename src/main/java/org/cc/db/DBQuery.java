package org.cc.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cc.model.CCProcObject;

/**
 * 查詢使用物件 CCProcUtils.exec(proc,"$in>db.rows@metaId,actId>$out");
 */
public class DBQuery {

    private final static Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");

    private List<QueryField> qrFields = new ArrayList<>();

    private StringBuilder sql = new StringBuilder();

    public DBQuery(CCProcObject proc, String cmd) {
        parser_cmd(proc, cmd);
    }

    private void parser_cmd(CCProcObject proc, String cmd) {
        sql.setLength(0);
        Stack<StringBuilder> stack = new Stack<StringBuilder>();
        Matcher match = p.matcher(cmd);
        while (match.find()) {
            String item = match.group(1);
            match.appendReplacement(sql, ""); // 直接清空
            if ("or".equals(item)) { // begin ${or} ..... ${end}
                stack.push(sql);
                sql = new StringBuilder();
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
                QueryField qf = new QueryField(proc, item);
                process_item(qf);
            }
        }
        match.appendTail(sql);
    }

    private void process_item(QueryField qf) {
        if(qf.getOp()==null){ // 非運算元

        } else {
            if(qf.getOp().getGroup()==1){
                process_op2(qf);
            }
        }
     
    }

    private void process_op2(QueryField qf) {
        if(qf.getValue()!=null){
            this.sql.append(" and ").append(qf.getOp().getCmd()).append(" ?");
            this.qrFields.add(qf);
        }
    }

    public List<QueryField> qrFileds() {
        return qrFields;
    }

    public String cmd() {
        return sql.toString();
    }

}
