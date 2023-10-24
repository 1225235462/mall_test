package com.ningdong.mall_test.search.controller;

import com.ningdong.common.to.es.SkuEsModel;
import com.ningdong.common.utils.R;
import com.ningdong.mall_test.search.service.ProductSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        try {
            productSaveService.productStatusUp(skuEsModels);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error(1,"es索引保存失败");
        }
    }
}
