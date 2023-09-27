package com.ningdong.mall_test.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.coupon.entity.HomeAdvEntity;

import java.util.Map;

/**
 * 首页轮播广告
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 09:59:38
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

