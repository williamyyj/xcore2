package org.cc.cp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.cc.data.CCData;
import org.cc.data.CCFileUtil;
import org.cc.json.CCJSON;
import org.cc.json.JSONObject;
import lombok.Data;

@Data
public class CodePushModel {

    private String prjId;
    private String cpId;
    private List<String> fileItems = null;
    private String base;
    private JSONObject cfg ;
    private String targetPath ;
    private String sourcePath ; 

    public CodePushModel(String base,String cpId, String prjId){
        this.base = base ; 
        this.cpId = cpId ;
        this.prjId = prjId ; 
        __init__fileList();
        __init_cpcfg();
    }

    private void __init_cpcfg() {
        JSONObject root = CCJSON.load(new File(base,"cp.json"));
        if(root!=null){
            cfg = root.optJSONObject(prjId);
            this.targetPath = cpId+"/"+prjId ;
            this.sourcePath = cfg.optString("$source");
            CCFileUtil.safePath(targetPath);
        }
    }

    private void __init__fileList() {
        try {
            File f = new  File(cpId, prjId+"_list.txt");
            fileItems = CCData.loadList(f,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            fileItems = new ArrayList<>();
        }
    }

    public void execute(){
        for(String item : fileItems){
            try{
           if(item.startsWith(cfg.optString("$vpjava"))){
                proc_java(item);
           } else {
            proc_webapp(item);
           }
        } catch (Exception e){
            e.printStackTrace();
        }
        }
    }

    protected void proc_java(String item) throws IOException{
        String target = item.replace(cfg.optString("$vpjava"),targetPath+"/WEB-INF/classes");
        target = target.replace(".java", ".class");
        File ft = new File(target);
        String src = item.replace(cfg.optString("$vpjava"),sourcePath+"/WEB-INF/classes");
        src = src.replace(".java", ".class");
        File fs = new File(src);
        CCFileUtil.safePath(ft.getParent());
        CCData.copy(fs, ft);
    }

    protected void proc_webapp(String item) throws IOException{
        String target = item.replace(cfg.optString("$vpwebapp"), targetPath);
        File ft = new File(target);
        String src = item.replace(cfg.optString("$vpwebapp"),sourcePath);
        File fs = new File(src);
        CCFileUtil.safePath(ft.getParent());
        CCData.copy(fs, ft);
    }

}
