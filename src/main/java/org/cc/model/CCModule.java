package org.cc.model;

import java.util.HashMap;
import java.util.Map;

import org.cc.json.CCCache;
import org.cc.json.JSONObject;

/**
 * 取代舊版metadata
 */
public abstract class CCModule {

    protected CCProcObject proc;

    protected Map<String,CCField> fldMap = new HashMap<>();

    protected JSONObject cfg;

    
    public static CCModule newInstance(CCProcObject proc){
        return null;

    }

    abstract String prefixMetadataPath();

    abstract String prefixActObjectPath();

    public JSONObject cfg(){
        return cfg;
    }


    public JSONObject loadMetadataCfg(String metaId) {
        return CCCache.load(prefixMetadataPath(), metaId);
    }

    public JSONObject loadActObjectCfg(String metaId) {
        return CCCache.load(prefixActObjectPath(), metaId);
    }

    public CCProcObject proc(){
        return this.proc;
    }

    public Map<String, CCField> fldMap(){
        return fldMap;
    }

    public CCField field(String line){
        return null;
    }

}
