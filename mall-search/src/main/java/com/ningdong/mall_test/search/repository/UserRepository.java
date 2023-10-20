package com.ningdong.mall_test.search.repository;

import com.ningdong.mall_test.search.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<UserEntity,String> {
}
