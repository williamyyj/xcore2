package org.cc.cp.item;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class PathFileFilter implements FileFilter {
    
    private Pattern p ; 
    private long lastUpdateDate;

    public PathFileFilter(String regex, long lastUpdateDate){
        this.p = Pattern.compile(regex);
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()){
            return true ;
        }
        return p.matcher(f.getName()).find() && f.lastModified()>lastUpdateDate;
    }
    
}
