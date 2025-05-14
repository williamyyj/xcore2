package org.cc.cp;

import java.io.IOException;
import org.cc.App;

public class ProjectPush {

    public static void main(String[] args) throws IOException {
    	//String tp = "D:/HHome/hyweb/維護/國防部/上版/2025/20250304";
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/庫存統計"; // using 20231101 
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/20240326"; // using 20230901 
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/2024_新功能_後台使用者管理"; // 
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/2024_新功能後台套版"; // 20240501
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2025/2025_後台持證者"; // 20240501
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2025/2025新功能"; // 20240501
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2025/2025_後台LOg"; // 20240501
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2025/20250430";
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2025/2025_tracker_log"; // 20250508
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/20241204_申請刪除陳報紀錄";
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/2024_新功能前台"; // 20240501
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/20240530"; // 
    	//String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2024/20240926"; 
    	String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2025/20250512"; // 20250508
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiq:baphiq",tp,"20250101");
    	//ProjectPushModel prj = new ProjectPushModel(App.project,"baphiq:admin",tp,"20250508"); // 20240501
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiqInft:mPosService",tp,"20240401");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiqInft:mPosCService",tp,"20240401");
        ProjectPushModel prj = new ProjectPushModel(App.project,"wpos:gipWebPos",tp,"20250401");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"wpos:mPosWClientAPI",tp,"20240301");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiqMedia:front",tp,"20230701");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiqMedia:admin",tp,"20230701");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"mnd:mndAdmin",tp,"20230901");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"mnd:mndFront",tp,"20250101");
        prj.push();
        
        //J2EEPathItem webapp = new J2EEPathItem(prj.cfg(),"$webapp");
        //webapp.push();
    }

}
