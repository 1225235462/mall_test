package com.ningdong.mall_test.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ningdong.mall_test.search.entity.SkuEsEntity;
import com.ningdong.mall_test.search.entity.UserEntity;
import com.ningdong.mall_test.search.repository.ProductRepository;
import com.ningdong.mall_test.search.repository.UserRepository;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MallSearchApplicationTests {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ProductRepository productRepository;

    @Resource
    ElasticsearchOperations elasticsearchOperations;

    @Test
    public void contextLoads() {
//        List<SkuEsEntity> res = productRepository.findBySkuTitleAndSpuId("华为",3L);

        System.out.println((int)Math.ceil((double) 5L/4L));
    }

}
