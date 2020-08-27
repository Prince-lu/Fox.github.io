package com.westworld.springbootcondition.config;

import com.westworld.springbootcondition.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
//    @Conditional(MyCondition.class)
//    @ConditionalOnClass("redis.clients.jedis.Jedis")
    public User user(){
        return new User();
    }
}
