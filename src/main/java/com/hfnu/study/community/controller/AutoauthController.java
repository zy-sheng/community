package com.hfnu.study.community.controller;

import com.hfnu.study.community.dto.AcessTokenDTO;
import com.hfnu.study.community.dto.GetHubUser;
import com.hfnu.study.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//将当前类作为路由API得承载者
public class AutoauthController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String ClientId;
    @Value("${github.client.secret}")
    private String ClientSecreat;
    @Value("${github.redirect.uri}")
    private String RedirectUri ;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
        AcessTokenDTO accessTokenDto = new AcessTokenDTO();
        accessTokenDto.setClient_id(ClientId);
        accessTokenDto.setClient_secret(ClientSecreat);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(RedirectUri);
        accessTokenDto.setState(state);
        String accessToken =gitHubProvider.getAccessToken(accessTokenDto);
        GetHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        //接收到参数返回到index页面.
        return "index";
    }

}
