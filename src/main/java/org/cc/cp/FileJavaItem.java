package org.cc.cp;

import java.io.File;
import org.cc.json.JSONObject;

public class FileJavaItem extends FileItem {

    protected File cf ; // class file 

    public FileJavaItem(JSONObject cfg, String name) {
        super(cfg, name);
    }

    @Override
    protected void init(JSONObject cfg, String name) {
 
        
    }
    
}
