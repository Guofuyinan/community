package life.lemon.community.controller;

import life.lemon.community.dto.*;
import life.lemon.community.model.User;
import life.lemon.community.provider.QQProvider;
import life.lemon.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthorizeController {//登录功能


    //QQ登录所需参数
    @Autowired
    private QQProvider qqProvider;

    @Value("${qq.client.id}")
    private String qqClientId;

    @Value("${qq.client.secret}")
    private String qqClientSecret;

    @Value("${qq.redirect.uri}")
    private String qqRedirectUri;

    @Value("${qq.grant.type}")
    private String qqGrantType;

    @Autowired
    private UserService userService;

    @GetMapping("/callbackQQ")
    public String Callback(@RequestParam(name = "code") String code,//1、获取Authorization Code
                             HttpServletResponse response) {
        QQAccessTokenDTO qqAccessTokenDTO = new QQAccessTokenDTO();
        qqAccessTokenDTO.setClient_id(qqClientId);
        qqAccessTokenDTO.setClient_secret(qqClientSecret);
        qqAccessTokenDTO.setCode(code);
        qqAccessTokenDTO.setRedirect_uri(qqRedirectUri);
        qqAccessTokenDTO.setGrantType(qqGrantType);

        //2、通过Authorization Code获取Access Token
        String accessToken = qqProvider.getAccessToken(qqAccessTokenDTO);
        //3、获取openId
        String openid = qqProvider.getOpenid(accessToken);

        //4、获取登录用户的信息
        QQUserDTO qqUserDTO = qqProvider.getUser(accessToken, qqClientId, openid);

        if (qqUserDTO != null) {
            //登录成功，写入cookie
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setOpenId(openid);
            user.setNickname(qqUserDTO.getNickname());
            user.setAvatarUrl(qqUserDTO.getFigureurl_qq_1());
            userService.createOrUpdate(user);

            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);//半年有效
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            //登录失败，重新登陆
            log.error("QQ登录失败");
            return "redirect:/";
        }
    }


    @GetMapping("/logout")//退出登录，移除Cookie和Session
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);//赋值Cookie为Null再添加一次，就是移除了
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
