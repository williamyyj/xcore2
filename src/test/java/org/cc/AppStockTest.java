package org.cc;

import java.io.File;

import org.cc.json.JSONObject;

public class AppStockTest {
    
    public static JSONObject $app = new JSONObject();

    public static String  google = "G:\\我的雲端硬碟";

    static {
        File f = new File(google);
        if(!f.exists()){
            google="/Users/william/Google 雲端硬碟";

        }
        
    }

    public static String  gstock = google+"/mydata/stock";

    public static String  project = google+"/myjob/resources/project";

    public static String base = project+"/stock";
    
    static {
        $app.put("$gstock",gstock);
        $app.put("$base",base);
    }
    

}
