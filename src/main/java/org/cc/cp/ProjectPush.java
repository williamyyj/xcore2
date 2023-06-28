package org.cc.cp;

import java.io.IOException;
import org.cc.App;
import org.cc.cp.item.J2EEPathItem;
import org.cc.cp.item.JavaPathItem;

public class ProjectPush {

    public static void main(String[] args) throws IOException {
        //String tp = "D:/HHome/hyweb/維護/國防部/上版/2023/20230329";
        //String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2023/2023新功能申請補登補正"; // using 20230501
    	String tp = "G:/我的雲端硬碟/myjob/hyweb/農藥/05_問題單/2023/20230617"; // using 20230601 
        ProjectPushModel prj = new ProjectPushModel(App.project,"baphiq:baphiq",tp,"20230601");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiq:admin",tp,"2023001");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"wpos:gipWebPos",tp,"20230601");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiqMedia:front",tp,"20221101");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"baphiqMedia:admin",tp,"20221101");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"mnd:mndAdmin",tp,"20230201");
        //ProjectPushModel prj = new ProjectPushModel(App.project,"mnd:mndFront",tp,"20230315");
        prj.push();
        
        //J2EEPathItem webapp = new J2EEPathItem(prj.cfg(),"$webapp");
        //webapp.push();
    }

}
