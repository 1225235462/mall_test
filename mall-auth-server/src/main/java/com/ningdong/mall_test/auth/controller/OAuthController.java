package com.ningdong.mall_test.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ningdong.common.utils.R;
import com.ningdong.common.vo.MemberRespVo;
import com.ningdong.mall_test.auth.Entity.GiteeUserInfoEntity;
import com.ningdong.mall_test.auth.feign.MemberFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@Slf4j
public class OAuthController {

    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/oauth2/gitee/success")
    public String authGitee(@RequestParam("code") String code, HttpSession session){
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://gitee.com/oauth/token?grant_type=authorization_code";
        MultiValueMap<String, Object> hashMap = new LinkedMultiValueMap<>();
        hashMap.add("code",code);
        hashMap.add("client_id","fdc51bb540a9c015fbaeba5bf38b9a54c801ecd46568786fdd661ea3efb64a60");
        hashMap.add("redirect_uri","http://auth.malltest.com/oauth2/gitee/success");
        hashMap.add("client_secret","c2cb80144b896bc83220dd333d81f46a69c0e309d9c2b66d5e4a8bec5996e650");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(hashMap);

        HashMap<String,String> res = restTemplate.postForObject(url, request, HashMap.class);

        if (!ObjectUtils.isEmpty(res)) {
            String access_token = res.get("access_token");

            HashMap<String, Object> parmaMap = new HashMap<>();
            parmaMap.put("access_token",access_token);

            GiteeUserInfoEntity res2 = restTemplate.getForObject("https://gitee.com/api/v5/user?access_token={access_token}", GiteeUserInfoEntity.class, parmaMap);
            res2.setAccessToken(res.get("access_token"));
            res2.setExpiresIn(String.valueOf(res.get("expires_in")));
//            log.error("=========={}",res2.getName());

            R r = memberFeignService.oauthLoginMember(res2);
            if (r.getCode() == 0){
                MemberRespVo data = r.getData(new TypeReference<MemberRespVo>() {});
                log.info("================{}",data.toString());

                session.setAttribute("loginUser",data);

                return "redirect:http://malltest.com";
            }else {
                return "redirect:http://auth.malltest.com/login.html";
            }
        }else {
            return "redirect:http://auth.malltest.com/login.html";
        }
    }
}
