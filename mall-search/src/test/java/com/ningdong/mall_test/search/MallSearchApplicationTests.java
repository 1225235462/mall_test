package com.ningdong.mall_test.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ningdong.mall_test.search.entity.UserEntity;
import com.ningdong.mall_test.search.repository.UserRepository;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MallSearchApplicationTests {

    @Autowired
    private UserRepository userRepository;



    @Test
    public void contextLoads() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("ning");
        userRepository.saveAll(null);
    }

//    @Test
//    public void save(){
//        IndexRequest indexRequest = new IndexRequest("users");
//        indexRequest.id("1");
//        User user = new User();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = null;
//        try {
//            json = objectMapper.writeValueAsString(user);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        indexRequest.source(json);
//    }
//
//    @Data
//    class User{
//        private String name;
//        private String gender;
//        private Integer age;
//    }

}
