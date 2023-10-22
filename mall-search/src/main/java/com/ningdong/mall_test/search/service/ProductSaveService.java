package com.ningdong.mall_test.search.service;

import com.ningdong.common.to.es.SkuEsModel;

import java.util.List;

public interface ProductSaveService{
    void productStatusUp(List<SkuEsModel> skuEsModels);
}
