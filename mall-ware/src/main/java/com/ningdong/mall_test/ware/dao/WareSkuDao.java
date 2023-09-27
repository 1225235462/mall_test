package com.ningdong.mall_test.ware.dao;

import com.ningdong.mall_test.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:31:12
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
