package org.cc.model;

import java.util.ArrayList;
import java.util.List;

import org.cc.ICCField;
import org.cc.json.CCPath;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;

public class CCMetadata {

    private ICCModule module;
    private String metaId;
    private JSONObject cfg;
    private List<ICCField> dbFields = new ArrayList<>();

    public CCMetadata(ICCModule module, String metaId) {
        __init_load_cfg(module, metaId);
    }

    private void __init_load_cfg(ICCModule module, String metaId) {
        this.module = module;
        this.metaId = metaId;
        cfg = module.loadMetadataCfg(metaId);
        __init＿fields();
        __init_dbFields();
    }

 

    private void __init＿fields() {
        JSONArray flds = cfg.optJSONArray("meta"); //這個不能空不防呆
        for(Object o : flds){
            JSONObject fldCfg = (JSONObject) o;
            fldCfg.put("type",module.proc().db().types().get(fldCfg.optString("dt")));    
            CCField field = new CCField();
            field.__init__(fldCfg);
            module.fldMap().put(metaId+"."+field.id(),field); //唯一如果有
            module.fldMap().put(field.id(),field);
        }
    }

    private void __init_dbFields() {
        String[] flds = CCPath.asStringArray(cfg,"$tbFields");
        for(String fld:flds){
            String key = metaId+"."+fld;
            dbFields.add(module.fldMap().get(key));
        }
    }

    public JSONObject cfg(){
        return cfg;
    }

    public List<ICCField> dbFields(){
        return this.dbFields;    
    }



}
