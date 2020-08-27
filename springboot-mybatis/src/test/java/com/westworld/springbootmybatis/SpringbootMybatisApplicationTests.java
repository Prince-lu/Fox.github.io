package com.westworld.springbootmybatis;

import com.westworld.springbootmybatis.domain.User;
import com.westworld.springbootmybatis.mapper.UserMapper;
import com.westworld.springbootmybatis.mapper.UserXmlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserXmlMapper userXmlMapper;

    @Test
    void contextLoads1() {
        List<User> user = userXmlMapper.findUser();

        for (User user1 : user) {
            System.out.println("user1 = " + user1);
        }
    }

    @Test
    void contextLoads() {
        List<User> user = userMapper.findUser();

        for (User user1 : user) {
            System.out.println("user1 = " + user1);
        }
    }

}
