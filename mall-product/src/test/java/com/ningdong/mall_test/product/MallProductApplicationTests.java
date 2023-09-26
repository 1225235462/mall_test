package com.ningdong.mall_test.product;

import com.ningdong.mall_test.product.entity.BrandEntity;
import com.ningdong.mall_test.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setName("索尼");
        brandService.save(brandEntity);
        System.out.println("success!");
    }

}
