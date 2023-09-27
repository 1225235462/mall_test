package com.ningdong.mall_test.coupon.dao;

import com.ningdong.mall_test.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 09:59:38
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
