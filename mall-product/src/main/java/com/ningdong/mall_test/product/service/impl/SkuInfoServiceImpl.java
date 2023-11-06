package com.ningdong.mall_test.product.service.impl;

import com.ningdong.mall_test.product.vo.SkuItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.common.utils.Query;

import com.ningdong.mall_test.product.dao.SkuInfoDao;
import com.ningdong.mall_test.product.entity.SkuInfoEntity;
import com.ningdong.mall_test.product.service.SkuInfoService;
import org.springframework.util.ObjectUtils;


@Slf4j
@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();

        Object key = params.get("key");
        if(!ObjectUtils.isEmpty(key)){
            wrapper.and(w -> {
                w.eq("sku_id",key).or().like("sku_name",key);
            });
        }

        Object brandId = params.get("brandId");
        if(!ObjectUtils.isEmpty(brandId)){
            wrapper.eq("brand_id",brandId);
        }

        Object catelogId = params.get("catelogId");
        if(!ObjectUtils.isEmpty(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }

        Object min = params.get("min");
        if(!ObjectUtils.isEmpty(min)){
            wrapper.ge("price",min);
        }

        Object max = params.get("max");
        if(!ObjectUtils.isEmpty(max)){
            try{
                BigDecimal num = new BigDecimal((String) max);

                if(num.compareTo(BigDecimal.ZERO) > 0){
                    wrapper.le("price",max);
                }
            }catch (Exception ignored){}
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id",spuId));
    }

    @Override
    public SkuItemVo item(Long skuId) {

        return null;
    }

}