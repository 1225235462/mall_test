package com.ningdong.mall_test.search.repository;

import com.ningdong.mall_test.search.entity.SkuEsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<SkuEsEntity,String> {
}
