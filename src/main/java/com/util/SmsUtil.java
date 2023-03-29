package com.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.json.JSONObject;

public class SmsUtil {

    public Integer SendMsg(String PhoneNumbers, String TemplateParam,Integer type) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5t9oncZMjVSNaHHN7Vf9", "*************************");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", "阿里云短信测试");
        if(type == 0){
            request.putQueryParameter("TemplateCode", "SMS_154950909");
        }else if(type == 1){
            request.putQueryParameter("TemplateCode", "SMS_154950909");
        }else if (type == 2){
            request.putQueryParameter("TemplateCode", "SMS_154950909");
        }
        request.putQueryParameter("TemplateParam", "{\"code\":\""+TemplateParam+"\"}");
        CommonResponse response=null;
        try {
            response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject(response.getData());
        if(result.getString("Code").equals("OK")){
            return 1;
        }
        if(result.getString("Code").equals("isv.MOBILE_NUMBER_ILLEGAL")){
            return 2;
        }
        return 0;
    }
}
