package org.cc;

import java.io.File;

public class CCTest {
    


    public static String project = "D:\\HHome\\GoogleDrive\\myjob\\resources\\project";
    
    static {
        File f = new  File(project);
        if(!f.exists()){
            project = "G:\\我的雲端硬碟\\myjob\\resources\\project";
        }
    }
    
    
    public static String prjBase(String prjId) {
    	return project+"\\"+prjId;
    }
    
    
    
}
