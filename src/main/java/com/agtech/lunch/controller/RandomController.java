package com.agtech.lunch.controller;

import com.agtech.lunch.manager.RandomRestaurantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018-11-30
 * Time: 13:56
 */
@RestController
public class RandomController {

    @Autowired
    private RandomRestaurantManager randomRestaurantManager;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/restaurant")
    public String restaurant() {
        return randomRestaurantManager.getRestaurantResult();
    }
}
