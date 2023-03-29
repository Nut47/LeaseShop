package com.util;

import java.util.Random;

public class GetCode {
    public static String phonecode(){
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("短信的六位验证码为："+verifyCode);
        return verifyCode;
    }
}