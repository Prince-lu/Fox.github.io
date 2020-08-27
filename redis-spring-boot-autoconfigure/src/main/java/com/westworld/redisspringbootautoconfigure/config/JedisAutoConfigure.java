package com.westworld.redisspringbootautoconfigure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
//@EnableConfigurationProperties(JedisProperties.class)     //启用jedisOnProperties
@ConditionalOnClass(Jedis.class)
public class JedisAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "jedis")
    public Jedis creatJedis(){
        System.out.println("++++++++++++++++++");
        return new Jedis("localhost",6379);
    }
}
