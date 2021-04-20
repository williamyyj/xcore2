package org.cc.model;


/**
 * ${base}/module/${mId}.json ---> only metadata
 * ${base}/module/${mId}_${actId}.json
 * ${base}/module/$meta/${metaId}.json
 * 
 */
public class CCModulePrjMode extends CCModule {
    
    private final static String prefixMeta  = "/module/$meta";
    private final static String prefixAct  = "/module/$meta";

    public CCModulePrjMode(CCProcObject proc){
        super(proc);
    }

    @Override
    public String prefixMetadataPath() {
        return proc.base()+prefixMeta;
    }
    @Override
    public String prefixActObjectPath() {
        return proc.base()+prefixAct;
    }

  

    

}
