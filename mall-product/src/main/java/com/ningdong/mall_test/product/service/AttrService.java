package com.ningdong.mall_test.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.product.entity.AttrEntity;
import com.ningdong.mall_test.product.vo.AttrGroupRelationVo;
import com.ningdong.mall_test.product.vo.AttrRespVo;
import com.ningdong.mall_test.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-26 12:21:56
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

