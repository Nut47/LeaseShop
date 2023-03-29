package com.util;

import java.util.Random;

public class KeyUtil {

    public static synchronized String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
    public static void main(String[] args){
        String s = KeyUtil.genUniqueKey();
        System.out.println(s);
    }
}
