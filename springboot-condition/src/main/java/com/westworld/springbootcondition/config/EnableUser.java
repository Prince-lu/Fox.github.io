package com.westworld.springbootcondition.config;

import org.springframework.context.annotation.Import;

@Import(UserConfig.class)
public @interface EnableUser {
}
