package com.ningdong.mall_test.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.ningdong.mall_test.member.exception.PhoneExistException;
import com.ningdong.mall_test.member.exception.UsernameExistException;
import com.ningdong.mall_test.member.feign.CouponFeignService;
import com.ningdong.mall_test.member.vo.MemberLoginVo;
import com.ningdong.mall_test.member.vo.MemberRegistVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.ningdong.mall_test.member.entity.MemberEntity;
import com.ningdong.mall_test.member.service.MemberService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.common.utils.R;



/**
 * 会员
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:10:11
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @GetMapping("/test")
    public R testFeign(){
        return R.error("错误");
    }

    @PostMapping("/login")
    public R loginMember(@RequestBody MemberLoginVo vo){
        MemberEntity entity = memberService.login(vo);

        if (!ObjectUtils.isEmpty(entity)) {
            return R.ok();
        }else {
            return R.error(15003,"账号密码错误");
        }
    }

    @PostMapping("/regist")
    public R registMember(@RequestBody MemberRegistVo vo){

        try {
            memberService.regist(vo);
        }catch (PhoneExistException e){
            return R.error(15001,e.getMessage());
        }catch (UsernameExistException e){
            return R.error(15002,e.getMessage());
        }

        return R.ok();
    }

    @RequestMapping("/coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("ning");

        R membercoupons = couponFeignService.membercoupons();

        return R.ok().put("member",memberEntity).put("coupons",membercoupons.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
