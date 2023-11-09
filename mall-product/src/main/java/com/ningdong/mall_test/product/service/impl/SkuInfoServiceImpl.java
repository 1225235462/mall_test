package com.ningdong.mall_test.product.service.impl;

import com.ningdong.mall_test.product.entity.*;
import com.ningdong.mall_test.product.service.*;
import com.ningdong.mall_test.product.vo.AttrGroupWithAttrsVo;
import com.ningdong.mall_test.product.vo.SkuItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.common.utils.Query;

import com.ningdong.mall_test.product.dao.SkuInfoDao;
import org.springframework.util.ObjectUtils;


@Slf4j
@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    ProductAttrValueService productAttrValueService;

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
        SkuItemVo skuItemVo = new SkuItemVo();

        SkuInfoEntity infoEntity = this.getById(skuId);
        skuItemVo.setInfo(infoEntity);

        List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
        skuItemVo.setImages(imagesEntities);

        List<SkuItemVo.SkuItemSaleAttrVo> saleAttrs = skuSaleAttrValueService.getSaleAttrsBySpuId(infoEntity.getSpuId());
        skuItemVo.setSaleAttr(saleAttrs);

        Long spuId = infoEntity.getSpuId();
        SpuInfoDescEntity descEntity = spuInfoDescService.getById(spuId);
        skuItemVo.setDesp(descEntity);

        List<AttrGroupWithAttrsVo> attrGroupWithAttrsVos = attrGroupService.getAttrGroupWithAttrsByCatelogId(infoEntity.getCatalogId());
        List<SkuItemVo.SpuItemAttrGroupVo> spuItemAttrGroupVos = attrGroupWithAttrsVos.stream().map(item -> {
            SkuItemVo.SpuItemAttrGroupVo spuItemAttrGroupVo = new SkuItemVo.SpuItemAttrGroupVo();
            spuItemAttrGroupVo.setGroupName(item.getAttrGroupName());

            List<SkuItemVo.SpuBaseAttrVo> baseAttrVos = item.getAttrs().stream().map(attr -> {
                Long attrId = attr.getAttrId();
                ProductAttrValueEntity productAttrValueEntity = productAttrValueService.getOne(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId).eq("attr_id", attrId));

                SkuItemVo.SpuBaseAttrVo spuBaseAttrVo = new SkuItemVo.SpuBaseAttrVo();
                spuBaseAttrVo.setAttrName(productAttrValueEntity.getAttrName());
                spuBaseAttrVo.setAttrValue(productAttrValueEntity.getAttrValue());
                return spuBaseAttrVo;
            }).collect(Collectors.toList());
            spuItemAttrGroupVo.setAttrs(baseAttrVos);
            return spuItemAttrGroupVo;
        }).collect(Collectors.toList());
        skuItemVo.setGroupAttrs(spuItemAttrGroupVos);


        return skuItemVo;
    }

}