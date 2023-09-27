package com.ningdong.mall_test.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.ware.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:31:12
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

