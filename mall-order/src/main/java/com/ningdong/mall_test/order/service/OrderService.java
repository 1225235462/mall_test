package com.ningdong.mall_test.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:25:26
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

