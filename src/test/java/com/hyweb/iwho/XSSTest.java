package com.hyweb.iwho;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class XSSTest {
    public static void main(String[] args){
        BufferedReader reader=null;
		try {

			String xsslist=  "src/test/resources/xss.set";
			FileInputStream fileInputStream =new FileInputStream(new File(xsslist));      
			InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
			reader=new BufferedReader(inputStreamReader);
			String input=null;
			while((input =reader.readLine())!=null){
                System.out.println(input.split("\t"));
			}
		} catch (IOException ex){
			ex.printStackTrace();
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
    }
}
