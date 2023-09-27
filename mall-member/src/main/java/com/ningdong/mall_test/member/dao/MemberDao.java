package com.ningdong.mall_test.member.dao;

import com.ningdong.mall_test.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:10:11
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
