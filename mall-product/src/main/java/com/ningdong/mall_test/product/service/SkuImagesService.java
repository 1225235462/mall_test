package com.ningdong.mall_test.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.product.entity.SkuImagesEntity;

import java.util.Map;

/**
 * sku图片
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-26 12:21:56
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

