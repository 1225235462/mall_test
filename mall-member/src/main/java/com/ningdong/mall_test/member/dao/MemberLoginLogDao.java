package com.ningdong.mall_test.member.dao;

import com.ningdong.mall_test.member.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:10:11
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
