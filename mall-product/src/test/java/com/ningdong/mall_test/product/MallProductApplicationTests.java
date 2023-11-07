package com.ningdong.mall_test.product;

import com.ningdong.mall_test.product.dao.SkuSaleAttrValueDao;
import com.ningdong.mall_test.product.vo.SkuItemVo;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


@SpringBootTest
class MallProductApplicationTests {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedissonClient redissonClient;

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;


    @Test
    void testDao(){
        List<SkuItemVo.SkuItemSaleAttrVo> saleAttrsBySpuId = skuSaleAttrValueDao.getSaleAttrsBySpuId(3L);
        System.out.println(saleAttrsBySpuId);
    }

    @Test
    void contextLoads() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("hello","world_"+ UUID.randomUUID().toString());

        System.out.println(operations.get("hello"));
    }

    @Test
    void testLock(){
        RLock lock = redissonClient.getLock("CatalogJson.lock");
        lock.unlock();
    }
}
