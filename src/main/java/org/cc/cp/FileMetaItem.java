package org.cc.cp;

import java.io.File;
import org.cc.json.JSONObject;

public class FileMetaItem extends FileItem {

    public FileMetaItem(JSONObject cfg, String name) {
        super(cfg, name);
    }

    @Override
    protected void init(JSONObject cfg, String name) {
        String fp = refString("$metaPath","fp", cfg.optString("fp"));
        String tp = refString("$metaPath","fp", cfg.optString("tp"));
        String funId = cfg.optString("$funId");
        String fullName = "".equals(name) ? funId+".json" : name+".json";
        this.src = new File (fp,fullName);
        this.target = new File(tp,fullName);
    }
    
}
