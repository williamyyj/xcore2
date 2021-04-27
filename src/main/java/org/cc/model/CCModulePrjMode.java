package org.cc.model;

import org.cc.json.JSONObject;

/**
 * ${base}/module/${mId}.json ---> only metadata
 * ${base}/module/${mId}_${actId}.json
 * ${base}/module/$meta/${metaId}.json
 * 
 */
public class CCModulePrjMode extends CCModule {
    
    private final String prefixMeta  = "/module/$meta";
    private final String prefixAct  = "/module";

  

    @Override
    public String prefixMetadataPath() {
        return proc.base()+prefixMeta;
    }
    @Override
    public String prefixActObjectPath() {
        return proc.base()+prefixAct;
    }    

}
