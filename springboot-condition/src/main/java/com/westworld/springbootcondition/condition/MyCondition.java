package com.westworld.springbootcondition.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;


public class MyCondition implements Condition {
    /**
     *
     * @param context         上下文对象，可以获取属性值、获取类加载器、获取BeanFactory等
     * @param metadata    元数据对象，用于获取注解定义的属性值
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        //通过注解属性导入指定坐标后创建Bean
        Map<String, Object> annotationName = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());

        String[] value =(String[]) annotationName.get("value");

        //判断有没有redis的包
        Boolean flag = true;

        try {
            for (String className : value) {
                Class<?> aClass = Class.forName(className);
            }
//            Class<?> aClass = Class.forName("redis.clients.jedis.Jedis");
        } catch (ClassNotFoundException e) {
            flag = false;
        }
        return flag;

       /* //判断有没有redis的包
        Boolean flag = true;

        try {
            Class<?> aClass = Class.forName("redis.clients.jedis.Jedis");
        } catch (ClassNotFoundException e) {
            flag = false;
        }
        return flag;*/
    }
}
