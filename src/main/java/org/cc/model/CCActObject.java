package org.cc.model;

import org.cc.json.JSONObject;

public class CCActObject {
    
    private ICCModule cm;

    private String aid;

    
    public CCActObject(ICCModule cm, String aid){
        this.cm = cm;   
    }

    public JSONObject cfg(){
        return null;
    }
}
