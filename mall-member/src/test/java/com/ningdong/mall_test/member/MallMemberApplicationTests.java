package com.ningdong.mall_test.member;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ningdong.mall_test.member.entity.MemberEntity;
import com.ningdong.mall_test.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallMemberApplicationTests {

    @Autowired
    MemberService memberService;

    @Test
    void contextLoads() {

        MemberEntity social_uid = memberService.getOne(new QueryWrapper<MemberEntity>().eq("social_uid", 5605937365L));
        System.out.println(social_uid);
    }

}
