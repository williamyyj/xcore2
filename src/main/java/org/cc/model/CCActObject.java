package org.cc.model;

import org.cc.json.JSONArray;
import org.cc.json.JSONObject;

/**
 *   [metaId, actId] 
 * @author william
 */
public class CCActObject extends JSONObject {

    private static final long serialVersionUID = -2355476586568452788L;
    private CCProcObject proc;
    private JSONObject mcfg ; 

    public CCActObject(CCProcObject proc, String line) {
        super();
        if (line != null && line.charAt(0) != '[') {
            line = "[" + line + "]";
        }
        __init__(proc, new JSONArray(line));
    }

    public CCActObject(CCProcObject proc, JSONArray ja) {
        __init__(proc, ja);
    }

    private void __init__(CCProcObject proc, JSONArray ja) {
        this.proc = proc;
        mcfg = CCJSON.load(proc.base()+"/module", ja.asString(0));
        if(ja.size()>1){
            putAll(mcfg.map(ja.asString(1)));
        }
        __init_metadata();  // 處理定義欄立
        __init_model();
    }

    public CCProcObject proc(){
        return proc;
    }

    private void __init_model() {
        put("$proc",proc);
        put("$mcfg",mcfg);
    }

    private void __init_metadata() {//相容
      ICCList list = mcfg.list("$metadata");
      list.forEach(o->{
          System.out.println("===== metaId :"+o);
          proc.metadata(o.toString()); // load meta
      });
    }

}
