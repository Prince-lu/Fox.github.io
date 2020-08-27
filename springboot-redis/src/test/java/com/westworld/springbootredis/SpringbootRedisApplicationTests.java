package com.westworld.springbootredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringbootRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setRedis(){
        //存入数据
     redisTemplate.boundValueOps("name").set("哈哈哈");

    }

    @Test
    public void getRedis() {
        String name =(String) redisTemplate.boundValueOps("name").get();
        System.out.println("name = " + name);
    }
}
