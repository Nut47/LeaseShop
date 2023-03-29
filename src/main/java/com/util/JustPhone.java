package com.util;

import java.util.regex.Pattern;

public class JustPhone {

    public static boolean justPhone(String phoneNum){
        if(phoneNum.length()!=11){
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if(!pattern.matcher(phoneNum).matches()){
            return false;
        }
        return true;
    }
}
