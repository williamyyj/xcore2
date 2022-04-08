package org.cc.json;

import java.io.File;

import org.cc.data.CCData;

public class CCJSON {

    public static JSONObject laod(String base, String id){

        return null;
        
    }

    public static JSONObject load(File f){
        return load(f,"UTF-8");
    }

    public static JSONObject load(File f, String enc){
        try {
            String json = CCData.loadString(f, enc);
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

    public static JSONObject mix(JSONObject p, JSONObject c) {
        JSONObject ret = new JSONObject(p);
        if (c != null) {
            c.forEach((k, v) -> ret.put(k, v));
        }
        return ret;
    }

    public static JSONObject get(JSONArray ja, String name, String value) {
        if (ja != null) {
            for (int i = 0; i < ja.length(); i++) {
                JSONObject row = ja.optJSONObject(i);
                if (row != null) {
                    if (value.equalsIgnoreCase(row.optString("name"))) {
                        return row;
                    }
                }
            }
        }
        return null;
    }
   
}
