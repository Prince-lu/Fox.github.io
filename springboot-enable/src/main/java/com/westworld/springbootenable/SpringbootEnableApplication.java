package com.westworld.springbootenable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

@SpringBootApplication
//@EnableUser
//@Import(User.class)
//@Import(UserConfig.class)
//@Import(MySelectImports.class)
public class SpringbootEnableApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SpringbootEnableApplication.class, args);


//        Object user = context.getBean(User.class);
//        System.out.println("user = " + user);
        Jedis bean = context.getBean(Jedis.class);
        System.out.println("bean = " + bean);


    }

    @Bean
    public Jedis jedis(){
        return  new Jedis("localhost",6379);
    }

}
