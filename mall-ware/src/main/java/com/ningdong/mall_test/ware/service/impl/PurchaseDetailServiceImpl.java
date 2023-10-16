package com.ningdong.mall_test.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.common.utils.Query;

import com.ningdong.mall_test.ware.dao.PurchaseDetailDao;
import com.ningdong.mall_test.ware.entity.PurchaseDetailEntity;
import com.ningdong.mall_test.ware.service.PurchaseDetailService;
import org.springframework.util.ObjectUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();

        Object key = params.get("key");
        if (!ObjectUtils.isEmpty(key)){
            wrapper.and(w -> w.eq("purchase_id",key).or().eq("sku_id",key));
        }

        Object status = params.get("status");
        if (!ObjectUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }

        Object wareId = params.get("wareId");
        if (!ObjectUtils.isEmpty(wareId)){
            wrapper.eq("ware_id",wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}