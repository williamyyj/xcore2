package org.cc.model;

import java.util.Map;

import org.cc.json.JSONObject;

public interface ICCModule {
    
    String prefixMetadataPath();

    String prefixActObjectPath();

    JSONObject loadMetadataCfg(String metaId);

    JSONObject loadActObjectCfg(String metaId);

    Map<String,CCField>  fldMap();

    CCField field(String line);

    CCProcObject proc();

}
