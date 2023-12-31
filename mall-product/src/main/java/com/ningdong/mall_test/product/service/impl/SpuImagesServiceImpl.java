package com.ningdong.mall_test.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.common.utils.Query;

import com.ningdong.mall_test.product.dao.SpuImagesDao;
import com.ningdong.mall_test.product.entity.SpuImagesEntity;
import com.ningdong.mall_test.product.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if(images != null && images.size() != 0){
            List<SpuImagesEntity> collect = images.stream().map(item -> {
                SpuImagesEntity entity = new SpuImagesEntity();
                entity.setSpuId(id);
                entity.setImgUrl(item);
                return entity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }

}