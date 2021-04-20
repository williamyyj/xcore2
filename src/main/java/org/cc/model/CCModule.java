package org.cc.model;

import java.util.HashMap;
import java.util.Map;

import org.cc.json.CCCache;
import org.cc.json.JSONObject;

/**
 * 取代舊版metadata
 */
public abstract class CCModule implements ICCModule {

    protected CCProcObject proc;

    protected Map<String,CCField> fldMap = new HashMap<>();

    public CCModule(CCProcObject proc) {
        this.proc = proc;
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
