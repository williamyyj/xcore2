package org.cc.model;

import org.cc.json.JSONObject;

public class CCModuleProdMode extends CCModule {

    private final String prefixMeta  = "/dp/metadata";
    private final String prefixAct  = "/dp/metadata";



    @Override
    public String prefixMetadataPath() {
        return proc.base()+prefixMeta;
    }
    @Override
    public String prefixActObjectPath() {
        return proc.base()+prefixAct;
    }
  
}
