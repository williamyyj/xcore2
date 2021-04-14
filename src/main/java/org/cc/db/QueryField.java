package org.cc.db;


import java.util.HashMap;
import java.util.Map;
import org.cc.ICCType;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;
import org.cc.model.CCProcObject;
import lombok.Data;

@Data
public class QueryField {
    

    private String id;
    private String dt;
    private String alias ;
    private ICCType<?> type ;
    private Object value;
    private DBQueryOP op;

    private static Map<String,DBQueryOP> opMap;
    static {
        if (opMap == null) {
            opMap = new HashMap<String, DBQueryOP>();
            opMap.put("=", new DBQueryOP("=","=",1));
            opMap.put(">", new DBQueryOP(">",">",1));
            opMap.put(">=",new DBQueryOP(">",">=",1));
            opMap.put("<", new DBQueryOP("<","<",1));
            opMap.put("<=",new DBQueryOP("<=","<=",1));
            opMap.put("!=",new DBQueryOP("!=","!=",1));
            opMap.put("<>",new DBQueryOP("<>","<>",1));
            opMap.put("$like", new DBQueryOP("$like","like",2)); //   xxx%
            opMap.put("$all", new DBQueryOP("$all","like",2));   //   %xxxx%
            //opMap.put("$range", new DBQueryOP("=","=",1));  //     fld  betten a and b 
            opMap.put("$set", new DBQueryOP("$set","=",2)); // for update 
            opMap.put("$rm", new DBQueryOP("$rm","",2));  // 移除最後 ","   
            opMap.put("@", new DBQueryOP("@","",2));  //    for table query or const 
            opMap.put("$expr", new DBQueryOP("$expr","",2));
        }
    }


    public QueryField(CCProcObject proc, String line){
        __parser_line(proc,line);
    }

    private void __parser_line(CCProcObject proc, String line) {
        String[] args = null;
        if (line.charAt(0) == '[') {
            JSONArray arr = new JSONArray(line);
            args = arr.asList().toArray(new String[0]);
        } else {
            args = line.split(",");
        }
        
        if(opMap.containsKey(args[0])){
            this.op = opMap.get(args[0]);
            this.id = args.length>1 ? args[1]: "";
            this.dt = args.length>2 ? args[2]: null;
            this.alias = args.length>3 ? args[3]: null;
        } else {
            this.id = args.length>0 ? args[0]: "";
            this.dt = args.length>1 ? args[1]: null;
            this.alias = args.length>2 ? args[2]: null;
        }

        if(dt!=null){
            this.type = proc.db().types().type(dt);
            JSONObject p = proc.params();
            Object value = null;
            if (p.containsKey(id)) {
                value = type.value(p.get(id));
            }
            if (value == null && alias != null && p.containsKey(alias)) {
                //Object av = row.get(alias);
                //System.out.println("===== debug av " + av);
                value = type.value(p.get(alias));
            }
            this.value = value;
        }
    }


    
}
