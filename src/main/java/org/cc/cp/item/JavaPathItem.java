package org.cc.cp.item;

import java.io.File;
import org.cc.json.JSONObject;

public class JavaPathItem extends PathItem {

    public JavaPathItem(JSONObject cfg, String name) {
        super(cfg, name);
        ff = new PathFileFilter("\\.java",cfg.optLong("lastUpdateDate"));
    }

    @Override
    protected void proc_add_file(File ffp) {
        File base = new File(fp);
        String fcp = ffp.getAbsolutePath().replace(base.getAbsolutePath(),cp);
        fcp = fcp.replace(".java", ".class");
        String ftp = ffp.getAbsolutePath().replace(base.getAbsolutePath(),tp);
        ftp = ftp.replace(".java", ".class");
        VFileItem vf = new VFileItem(ffp,new File(fcp),new File(ftp));
    	System.out.println(vf);
        files.add(vf);        
    }
    
    
    

    
}
