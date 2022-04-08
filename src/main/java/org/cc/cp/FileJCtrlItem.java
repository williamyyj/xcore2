package org.cc.cp;

import java.io.File;
import org.cc.json.JSONObject;

/**
 *  jsp controller 
 */
public class FileJCtrlItem extends FileItem {

    public FileJCtrlItem(JSONObject cfg, String name) {
        super(cfg, name);
    }

    @Override
    protected void init(JSONObject cfg, String name) {
        String fp = refString("$ctrPath","fp", cfg.optString("fp"));
        String tp = refString("$ctrPath","fp", cfg.optString("tp"));
        String funId = cfg.optString("$funId");
        String fullName = "".equals(name) ? funId+".jsp" : funId+"_"+name+".jsp";
        this.src = new File(fp,fullName);
        this.target = new File(tp,fullName);
    }
    
}
