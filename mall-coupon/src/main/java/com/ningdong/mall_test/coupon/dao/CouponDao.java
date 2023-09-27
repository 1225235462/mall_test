package com.ningdong.mall_test.coupon.dao;

import com.ningdong.mall_test.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 09:59:38
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
