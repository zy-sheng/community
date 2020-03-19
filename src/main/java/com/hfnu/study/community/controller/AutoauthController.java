package com.hfnu.study.community.controller;

import com.hfnu.study.community.dto.AcessTokenDTO;
import com.hfnu.study.community.dto.GetHubUser;
import com.hfnu.study.community.mapper.UserMapper;
import com.hfnu.study.community.model.User;
import com.hfnu.study.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
    private String RedirectUri;

    //spring对象会事先将方法实例化放到容器里面
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response
    ) {
        AcessTokenDTO accessTokenDto = new AcessTokenDTO();
        accessTokenDto.setClient_id(ClientId);
        accessTokenDto.setClient_secret(ClientSecreat);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(RedirectUri);
        accessTokenDto.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        GetHubUser githubUser = gitHubProvider.getUser(accessToken);

        //注意此处缺一个判断gitHubUser.getId()!=null
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            //插入数据库，完成写入
            userMapper.insert(user);
            //登录成功，写cookie和Sesison
            response.addCookie(new Cookie("token", token)); //写入token


            // request.getSession().setAttribute("user",githubUser);
            return "redirect:/";

        } else {
            return "redirect:/";
            //登录失败，重新写入
        }

    }

}
