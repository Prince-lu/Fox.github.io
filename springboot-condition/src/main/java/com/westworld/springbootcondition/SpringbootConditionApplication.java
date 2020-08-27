package com.westworld.springbootcondition;


import com.westworld.springbootcondition.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootConditionApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SpringbootConditionApplication.class, args);

        Object bean = context.getBean("user");
        System.out.println("bean = " + bean);
    }

}
