package org.cc.cp.item;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import org.cc.json.JSONObject;

public abstract class PathItem {
    
    protected String name ;
    
    protected JSONObject cfg ; 

    protected List<VFileItem> files = new ArrayList<>(); 

    protected String fp ;

    protected String cp ;

    protected String tp ;

    protected FileFilter ff ; 

    public PathItem(JSONObject cfg , String name){
        this.name = name ; 
        this.cfg = cfg;
        init();
    }

    protected void init() {
        fp = refString(name,"fp", cfg.optString("fp"));   
        cp = refString(name+"_cp","fp", cfg.optString("fp"));  
        tp = refString(name+"_tp","tp", cfg.optString("tp"));     
        if(tp.length()==0){
            tp = cfg.optString("tp");
        }
    }

    
    public String refString(String key , String id , String v){
        String pattern = "\\{"+id+"\\}";
        String item = cfg.optString(key);
        item = item.replaceAll(pattern, v);
        return item;
    }

    public void push(){
        if(files.size()==0){
            pushFile(new File(fp));
        }
        File ftp = new File(tp);
        if(!ftp.exists()){
            ftp.mkdirs();
        }
        for(VFileItem item:files){
            try{
                item.push();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected void pushFile(File ffp ){
        if(ffp.isDirectory()){
            File[] list = ffp.listFiles(ff);
            for(File item:list){
                pushFile(item);
            }
        } else {
           proc_add_file(ffp);
        }
    }

    protected abstract void proc_add_file(File ffp);

    public static PathItem create(JSONObject cfg , String id , String classId){
    	PathItem ret = null;
        switch (classId){
            case "java" : ret = new JavaPathItem(cfg,id); break;
            case "j2ee" : ret = new J2EEPathItem(cfg,id); break;
        } 
        if(ret==null) {
        	System.out.println("===== error  : "+ id+"--->"+classId);
        } else {
        	System.out.println("===== create : "+ id+"--->"+classId);        	
        }
        return ret;
    } 


    
}
