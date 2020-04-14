package com.hfnu.study.community.send;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

@Component
public class sendSms {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    //对应你阿里云账户的 accessKeyId
    private static final String accessKeyId = "LTAI4FtAZj7xSVuxj1xMWFpE";
    //对应你阿里云账户的 accessKeySecret
    private static final String accessKeySecret = "4QykorEFmO4v2JCyZKlJDMtDs2Az20";
    //对应签名名称
    private static final String signName = "论坛交流吧";
    //对应模板代码
    private static final String templateCode = "SMS_184221705";

    private static String mobile_code = getCode();

    public static String send(String num) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", num);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":" + mobile_code + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return mobile_code;
    }

    public static String getCode() {
        String ranfdom = (int) ((Math.random() * 9 + 1) * 100000) + "";
        System.out.println(ranfdom);
        return ranfdom;
    }

}
