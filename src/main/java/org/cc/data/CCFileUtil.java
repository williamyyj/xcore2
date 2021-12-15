package org.cc.data;

import java.io.File;

public class CCFileUtil {
    
    public static void safePath(String item){
        safePath(new File(item));
    }

    public static void safePath(File file){
       if(!file.exists()){
            file.mkdirs();
       } 
    }
}
