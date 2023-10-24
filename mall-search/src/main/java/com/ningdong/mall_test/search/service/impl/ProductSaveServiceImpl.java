package com.ningdong.mall_test.search.service.impl;

import com.ningdong.common.to.es.SkuEsModel;
import com.ningdong.mall_test.search.entity.SkuEsEntity;
import com.ningdong.mall_test.search.repository.ProductRepository;
import com.ningdong.mall_test.search.service.ProductSaveService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    ProductRepository productRepository;

    public void productStatusUp(List<SkuEsModel> skuEsModels) throws Exception{
        List<SkuEsEntity> entities = skuEsModels.stream().map(item -> {
            SkuEsEntity skuEsEntity = new SkuEsEntity();
            BeanUtils.copyProperties(item, skuEsEntity);
            return skuEsEntity;
        }).collect(Collectors.toList());
        productRepository.saveAll(entities);
    }
}
