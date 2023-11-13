package com.ningdong.mall_test.auth;

import com.ningdong.common.utils.R;
import com.ningdong.mall_test.auth.feign.MemberFeignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
class MallAuthServerApplicationTests {

    @Autowired
    MemberFeignService memberFeignService;

    @Test
    void contextLoads() {
        R r = memberFeignService.testFeign();
        System.out.println(r.get("msg"));

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1",(String) r.get("msg"));
    }

}
