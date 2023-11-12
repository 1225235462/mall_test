package com.ningdong.mall_test.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.member.entity.MemberEntity;
import com.ningdong.mall_test.member.exception.PhoneExistException;
import com.ningdong.mall_test.member.exception.UsernameExistException;
import com.ningdong.mall_test.member.vo.MemberLoginVo;
import com.ningdong.mall_test.member.vo.MemberRegistVo;

import java.util.Map;

/**
 * 会员
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:10:11
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);

    void checkPhoneUnique(String phone) throws PhoneExistException;

    void checkUsernameUnique(String username) throws UsernameExistException;

    MemberEntity login(MemberLoginVo vo);
}

