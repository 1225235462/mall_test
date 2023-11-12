package com.ningdong.mall_test.auth.feign;

import com.ningdong.common.utils.R;
import com.ningdong.mall_test.auth.vo.UserLoginVo;
import com.ningdong.mall_test.auth.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-member")
public interface MemberFeignService {
    @PostMapping("/member/member/regist")
    public R registMember(@RequestBody UserRegistVo vo);

    @GetMapping("/member/member/test")
    public R testFeign();

    @PostMapping("/member/member/login")
    public R loginMember(@RequestBody UserLoginVo vo);
}
