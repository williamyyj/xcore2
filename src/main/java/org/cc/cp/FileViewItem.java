package org.cc.cp;

import java.io.File;
import org.cc.json.JSONObject;

public class FileViewItem extends FileItem {

    public FileViewItem(JSONObject cfg, String name) {
        super(cfg, name);
    }

    @Override
    protected void init(JSONObject cfg, String name) {
        String fp = refString("$viewPath","fp", cfg.optString("fp"));   
        String tp = refString("$viewPath","fp", cfg.optString("tp"));        
        String funId = cfg.optString("$funId");
        String fullName = "".equals(name) ? funId+".jsp" : funId+"_"+name+".jsp";
        this.src = new File (fp,fullName);
        this.target = new File(tp,fullName);
    }
    
 
}
