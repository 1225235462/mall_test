package com.ningdong.mall_test.search;

import com.ningdong.mall_test.search.entity.UserEntity;
import com.ningdong.mall_test.search.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class MallSearchApplicationTests {

    @Autowired
    private UserRepository userRepository;



    @Test
    public void contextLoads() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("ning");
        userRepository.save(userEntity);
    }

}
