package org.cc.model;

import org.cc.json.JSONArray;
import org.cc.json.JSONObject;

/**
 *   mid,aid,....
 *   {}
 */
public abstract class CCCmdModuleString {

    public static CCCmdModuleString newInstance(String line){
        if(line !=null){
            line = line.trim();
            char c = line.length()>0 ? line.charAt(0) : 0;
            switch(c){
                case '[' :  new CCCmdModuleJAString(line);
                case '{' : new CCCmdModuleJOString(line);
                default :
                    new CCCmdModuleJAString(line);
            }

        } 
        new RuntimeException("CCCmdModuleString can't "+line);
        return null;    
    }

    public abstract String mid();

    public abstract String aid();
 
    public abstract JSONObject jo();
    
    public abstract JSONArray ja();

}
