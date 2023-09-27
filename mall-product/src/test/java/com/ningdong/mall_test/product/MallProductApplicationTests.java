package com.ningdong.mall_test.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ningdong.mall_test.product.entity.BrandEntity;
import com.ningdong.mall_test.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();

//        brandEntity.setName("索尼");
//        brandService.save(brandEntity);
//        System.out.println("success!");

//        brandEntity.setBrandId(6L);
//        brandEntity.setDescript("信仰");

//        brandService.updateById(brandEntity);

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id",6L));
        list.forEach((item)->{
            System.out.println(item);
        });
    }

}
