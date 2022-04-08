package org.cc.cp;


import java.io.IOException;
import org.cc.App;

public class ModulePush {
    
    public static void main(String[] args) throws IOException{
        String tp = "D:/HHome/GoogleDrive/myjob/hyweb/農藥/05_問題單/2022/2022新功能";
        ModulePushModel mp = new ModulePushModel(App.base("baphiq"), "admin:count_records",tp,"20220320");
        mp.pushFiles();
    
    }
}
