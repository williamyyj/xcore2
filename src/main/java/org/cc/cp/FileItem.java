package org.cc.cp;

import java.io.File;
import org.cc.json.JSONObject;

public abstract class FileItem {

    protected File src ;

    protected File target ; 

    protected JSONObject cfg;

    public FileItem(JSONObject cfg, String name){
        this.cfg = cfg;
        init(cfg,name);
    }

    protected abstract void init(JSONObject cfg, String name);

    public String refString(String key , String id , String v){
        String pattern = "\\{"+id+"\\}";
        String item = cfg.optString(key);
        item = item.replaceAll(pattern, v);
        return item;
    }

    public File src(){
        return this.src;
    }

    public File target(){
        return this.target;
    }
    
}
