package org.cc.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import org.cc.CCTest;

public class CSVTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String prjBase = CCTest.prjBase("sonix");
		String prjTestBase = prjBase+"\\test";
		File f = new File( prjTestBase, "sendMailList_v20230420.csv");
	       CSVParser csvParser = new CSVParser(new FileReader(f), CSVFormat.DEFAULT);
	        for (CSVRecord record : csvParser) {
	        	//TID,TNAME,DSID,DSTITLE,CREATETIME,EDITTIME
	        	String dsId = record.get(2);
	        	String editTime = record.get(5);
	        	System.out.println("update dataset set editTime=CURRENT_DATE where dsId="+dsId+";");
	        	//if("(null)".equals(editTime)) {
	        	//	System.out.println("update dataset set editTime=null where dsId="+dsId+";");
	        	//} else {
	        	//	System.out.println("update dataset set editTime=to_date('"+editTime+"','YYYY-MM-DD HH24:MI:SS') where dsId="+dsId+";");
	        	//}
	        	
	        }
	       
	}
}
