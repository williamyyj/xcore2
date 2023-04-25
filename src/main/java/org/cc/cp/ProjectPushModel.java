package org.cc.cp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cc.cp.item.JavaPathItem;
import org.cc.cp.item.PathItem;
import org.cc.json.CCJSON;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;
import org.cc.util.CCDateUtils;

public class ProjectPushModel {
    private String prjBase; 
    private String prjId;
    private String moduleId; 
    private String tp;
    private JSONObject cfg;
    private String lastUpdateDate; 
    private List<PathItem> pList = new ArrayList<>();
     
    
    /**
     * 
     * @param base
     * @param key
     * @param tp
     * @param beginDate
     */
    public ProjectPushModel(String prjBase,String key , String tp,String lastUpdateDate){
        this.prjBase = prjBase ;
        String[] items = key.split(":");
        this.prjId = items[0];
        this.moduleId = items.length>1 ? items[1] : "";
        this.tp = tp ;
        this.lastUpdateDate = lastUpdateDate;
        __init_cfg();
        __init_path();
    }
    
    protected void __init_cfg() {
        File cfgPath = new File(prjBase+"/"+prjId,moduleId+".json");
        System.out.println("===== cfgPath ::: "+cfgPath);
        cfg = CCJSON.load(cfgPath);
        cfg.put("tp",tp+"/"+moduleId);
        Date d = CCDateUtils.toDate(lastUpdateDate);
        cfg.put("lastUpdateDate",d.getTime());
        System.out.println(cfg.toString(4));
    }

    private void __init_path() {
    	pList.clear();
        JSONArray ja = cfg.optJSONArray("path");
        for(Object item : ja){
            String items[] = ((String)item).split(":");
            proc_one_path(items[0],items[1]);
        }
    }

    private void proc_one_path(String path , String classId) {
    	pList.add( PathItem.create(cfg, path, classId));
    }

    public JSONObject cfg(){
        return cfg;
    }
    
    public void push() {
    	for(PathItem item : pList) {
    		item.push();
    	}
    }
}
