package com.ningdong.mall_test.auth.controller;

import com.ningdong.common.constant.AuthServiceConstant;
import com.ningdong.common.utils.R;
import com.ningdong.mall_test.auth.feign.ThirdPartyService;
import com.ningdong.mall_test.auth.vo.UserRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    ThirdPartyService thirdPartyService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sms/sendcode")
    @ResponseBody
    public R sendCode(@RequestParam("phone") String phone){

        String codeValue = stringRedisTemplate.opsForValue().get(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (ObjectUtils.isEmpty(codeValue)) {
            String code = String.valueOf((int)((Math.random()*9+1)*100000));
            stringRedisTemplate.opsForValue().set(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + phone, code + "_" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);
            thirdPartyService.sendCode(phone, code);
        }else{
            long beforeTime = Long.parseLong(codeValue.split("_")[1]);
            if(System.currentTimeMillis() - beforeTime > 60000){
                String code = String.valueOf((int)((Math.random()*9+1)*100000));
                stringRedisTemplate.opsForValue().set(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + phone, code + "_" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);
                thirdPartyService.sendCode(phone, code);
            }else{
                return R.error(10002,"验证码发送过于频繁");
            }
        }

        return R.ok();
    }

    @PostMapping("/regist")
    public String regist(@Valid UserRegistVo vo, BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()){
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,(entity1, entity2) -> entity1));

            redirectAttributes.addFlashAttribute("errors",errors);

            return "redirect:http://auth.malltest.com/register.html";
        }

        return "redirect:http://auth.malltest.com/login.html";
    }
}
