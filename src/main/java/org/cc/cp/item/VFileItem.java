package org.cc.cp.item;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import org.cc.data.CCData;
import org.cc.text.TextUtils;
import org.cc.util.CCDateUtils;


/**
 * 
 */
public class VFileItem {

    private File fp ; 

    private File cp ;
    
    private File tp ; 

    public VFileItem(File fp, File cp , File tp) {
      this.fp = fp;
      this.cp = cp ;
      this.tp = tp;
    }

    public void push() throws IOException{
        if(cp!=null){ 
            CCData.copy(cp, tp); // java -> class -> tp 

        } else {
            CCData.copy(fp, tp); // jsp -> tp
        }
        tp.setLastModified(fp.lastModified());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String fpDate = TextUtils.df("yyyy-MM-dd",  new Date(fp.lastModified()));
        sb.append("fp-->"+ fpDate +":::"+ fp).append("\r\n");
        if(cp!=null){
            sb.append("cp-->"+ cp).append("\r\n");
        }
        sb.append("tp-->"+ tp).append("\r\n");
        return sb.toString();
    }

}
