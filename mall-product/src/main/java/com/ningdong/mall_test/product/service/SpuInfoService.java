package com.ningdong.mall_test.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.product.entity.SpuInfoDescEntity;
import com.ningdong.mall_test.product.entity.SpuInfoEntity;
import com.ningdong.mall_test.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-26 12:21:56
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

