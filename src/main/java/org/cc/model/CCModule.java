package org.cc.model;

import java.util.HashMap;
import java.util.Map;
import org.cc.ICCField;

/**
 * 取代舊版metadata
 */
public class CCModule {

    protected Map<String,ICCField>  fields = new HashMap<>(); //整合全部CCField



    public CCModule(String base, String path){
        
    }


}
