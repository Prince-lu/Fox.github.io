package com.westworld.test;

import com.westworld.springbootprofile.SpringbootProfileApplication;
import com.westworld.springbootprofile.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootProfileApplication.class)
public class userServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void userserviceTest(){
        userService.userService();

    }


}


