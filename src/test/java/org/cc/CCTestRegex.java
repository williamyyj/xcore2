package org.cc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class CCTestRegex {

    @Test
    public void test_email(){
        List<String> emails = new ArrayList<>();
        emails.add("user@domain.com");
        emails.add("user@domain.co.in");
        emails.add("user1@domain.com");
        emails.add("user.name@domain.com");
        emails.add("user#@domain.co.in");
        emails.add("user@domaincom");
         
        //Invalid emails
        emails.add("user#domain.com");
        emails.add("@yahoo.com");
        emails.add("williamyyj@tpe.hyweb.com.tw");


        String regex  = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		Pattern p = Pattern.compile(regex);
        for(String email : emails){
            Matcher matcher = p.matcher(email);
            System.out.println(email +" : "+ matcher.matches());
        }

    }

}
