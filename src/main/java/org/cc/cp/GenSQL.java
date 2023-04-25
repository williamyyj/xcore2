package org.cc.cp;

import java.io.File;
import java.util.List;
import org.cc.data.CCData;

public class GenSQL {
    public static void main(String[] args) throws Exception{
        List<String> buf = CCData.loadList(new File("d:\\xml.csv"), "UTF-8");
        StringBuilder sb = new StringBuilder();
        System.out.println(buf.size());
        for(String line:buf){
             String[] items = line.split(",");
             String sql = "update archives_xml_status set dirStatus='%s' where xml_id = '%s' ; ";
             String sqlLine = String.format(sql, items[1],items[0]);
             sb.append(sqlLine).append("\r\n");
       }
       CCData.saveText(new File("d:\\archives.sql"), sb.toString(), "UTF-8");
    }
}
