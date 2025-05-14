package org.cc.cp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.cc.data.CCData;
import org.cc.json.CCJSON;
import org.cc.json.JSONObject;
import org.cc.text.TextUtils;

public class ModulePushModel {
    private String base; 
    private String prjId;
    private String moduleId; 
    private String tp;
    private JSONObject prjCfg;
    private JSONObject mCfg;
    private JSONObject cfg;
    private List<FileItem> files ;
    private String beginDate; 
     
    
    /**
     * 
     * @param base  共用專案位置
     * @param key prjId:moduleId
     * @param tp 目地Path
     */
    public ModulePushModel(String base,String key , String tp,String beginDate){
        this.base = base ;
        String[] items = key.split(":");
        this.prjId = items[0];
        this.moduleId = items[1];
        this.tp = tp ;
        this.beginDate = beginDate;
        __init_cfg();
    }

    public List<FileItem> files(){
        if(files==null){
            files = new ArrayList<>();
            proc_files_controller();
            proc_files_metadata();
            proc_files_views();
        }
        return files;
    }

    private void proc_files_views() {

        for(Object o : cfg.optJSONArray("$views")){
            FileItem item = new FileViewItem(cfg,o.toString());
            files.add(item);
        } 
    }

    private void proc_files_controller() {

        for(Object o : cfg.optJSONArray("$ctr")){
            FileItem item = new FileJCtrlItem(cfg,o.toString());
            files.add(item);
        }
    }

    private void proc_files_metadata() {
        FileItem item = new FileMetaItem(cfg,cfg.optString("$metaId"));
        files.add(item);
    }

    protected void __init_cfg() {
        prjCfg = CCJSON.load(new File(base+"/module",prjId+".json"));
        mCfg = CCJSON.load(new File(base+"/module/"+prjId,moduleId+".json"));
        cfg = new JSONObject(prjCfg);
        cfg.putAll(mCfg);
        cfg.put("tp",tp+"/"+prjId);
        cfg.put("beginDate",beginDate);
    }

    public void pushFiles() throws IOException {
        Date beginDate = cfg.optDate("beginDate");
        long beginTimestamp = beginDate.getTime();
        for(FileItem item : files()){
            if(item.src().exists() && item.src().lastModified()> beginTimestamp ){
                CCData.copy(item.src(), item.target());
                Date last = new Date(item.src().lastModified());
                System.out.println("Copy src : "+ item.src()+"-->"+ TextUtils.df("yyyy/MM/dd", last));
            } else {
                if(!item.src().exists()){
                    System.out.println("Not Exists src : "+ item.src());
                } else {
                    Date last = new Date(item.src().lastModified());
                    System.out.println("Skip src : "+ item.src()+"-->"+ TextUtils.df("yyyy/MM/dd", last));
                }
            }
        }
    }
  
    


}
