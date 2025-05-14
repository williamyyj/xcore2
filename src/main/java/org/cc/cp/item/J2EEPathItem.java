package org.cc.cp.item;

import java.io.File;
import org.cc.json.JSONObject;

public class J2EEPathItem extends PathItem {
    private String rp = "([^\\s]+(\\.(?i)(tag|js|css|jsp|json|mv|htm|html|xml|xsl|properties|png|jpeg|gif|doc|odt|ods|pdf|xls|xlsx|jar))$)";
    public J2EEPathItem(JSONObject cfg, String name) {
        super(cfg, name);
        ff = new PathFileFilter( rp,cfg.optLong("lastUpdateDate"));
    }

    /**
     *  id : soruce code 
     *  $id_cp : bin code 
     *  $id_tp : 目的目錄
     */
    @Override
    protected void proc_add_file(File ffp) {
        File base = new File(fp);
        //String fcp = ffp.getAbsolutePath().replace(base.getAbsolutePath(),cp);
        //fcp = fcp.replace(".java", ".class");
        String ftp = ffp.getAbsolutePath().replace(base.getAbsolutePath(),tp);
        //ftp = ftp.replace(".java", ".class");
        VFileItem vf = new VFileItem(ffp,null,new File(ftp));
        files.add(vf);        
        System.out.println(vf);
    }

    
}
