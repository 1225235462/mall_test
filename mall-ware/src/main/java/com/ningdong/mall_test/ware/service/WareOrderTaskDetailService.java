package com.ningdong.mall_test.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:31:12
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

