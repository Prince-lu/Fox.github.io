package com.westworld.userdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findUser")
    public String findUser(){
        String url = "http://127.0.0.1:8080/user";
        String forObject = restTemplate.getForObject(url, String.class);
        return forObject;
    }
}
