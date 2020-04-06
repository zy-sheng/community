package com.hfnu.study.community.provider;

import com.alibaba.fastjson.JSON;
import com.hfnu.study.community.dto.AcessTokenDTO;
import com.hfnu.study.community.dto.GetHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//紧紧得将当前类初始化到容器得上下文,就等于说这个对象就自动得实例化放在了一个池子中
public class GitHubProvider {

    //实现post请求
    public String getAccessToken(AcessTokenDTO accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
            Request request = new Request.Builder()
                    .url(" https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string =  response.body().string();
                //获取code
                System.out.println(string);
                String token = string.split("&")[0].split("=")[1];
              return token;
        } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public GetHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string =  response.body().string();

            //自动得将string对象转化为java得一个类对象
            GetHubUser getHubUser = JSON.parseObject(string , GetHubUser.class);
            return getHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
