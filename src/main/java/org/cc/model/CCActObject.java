package org.cc.model;

import org.cc.json.CCCache;
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
        mcfg = CCCache.load(proc.base()+"/module", ja.optString(0));
        if(ja.length()>1){
            putAll(mcfg.optJSONObject(ja.optString(1)));
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
      JSONArray list = mcfg.optJSONArray("$metadata");
      list.forEach(o->{
          System.out.println("===== metaId :"+o);
          proc.metadata(o.toString()); // load meta
      });
    }

}
