package org.cc.model;

import java.util.ArrayList;
import java.util.List;

import org.cc.json.JSONObject;

public class CCActObject {
    
    private ICCModule cm;

    private String aid;

    private JSONObject cfg ;
    
    private BiFetchFields fetchFields = new BiFetchFields();
    
    public CCActObject(ICCModule cm, String aid){
        this.cm = cm;   
        this.aid = aid ;
        __init_act();
    }

    private void __init_act() {
        this.cfg = cm.cfg().optJSONObject(aid);
        if(cfg!=null){
            __init_fields(cfg.opt("$fields"));
        }
    }

    private void __init_fields(Object o) {
		if(o instanceof String) {
			
		}
		
	}

	public JSONObject cfg(){
        return cfg;
    }
    
    public List<CCField> fields(){
    	return fields("$fields");
    }
    
	public List<CCField> fields(String id) {
		Object o = cfg.opt(id);
		o = o != null ? o : cfg.opt("$fields");
		return fetchFields.apply(cm, o);
	}
	
	public  List<CCField> dbFields(String metaId){
		return cm.dbFields(metaId);
	}
	
	
}
