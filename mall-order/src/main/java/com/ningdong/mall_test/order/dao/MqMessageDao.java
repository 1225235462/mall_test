package com.ningdong.mall_test.order.dao;

import com.ningdong.mall_test.order.entity.MqMessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:25:26
 */
@Mapper
public interface MqMessageDao extends BaseMapper<MqMessageEntity> {
	
}
