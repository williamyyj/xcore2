package org.cc.json;

import java.io.File;

import org.cc.data.CCData;

public class CCJSON {

    public static JSONObject laod(String base, String id){

        return null;
        
    }

    public static JSONObject load(File f){
        try {
            String json = CCData.loadString(f, "UTF-8");
            return new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }  
    }

    public static JSONObject line(Object line) {
        if (line instanceof JSONObject) {
            return (JSONObject) line;
        } else if (line instanceof String) {
            String text = ((String) line).trim();
            text = (text.charAt(0) == '{' ? text : "{" + text + "}");
            return new JSONObject(text);
        }
        return null;
    }

    
   
}
