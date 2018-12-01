package com.agtech.lunch.controller;

import com.agtech.lunch.manager.AiManager;
import com.agtech.lunch.manager.RandomRestaurantManager;
import com.agtech.lunch.timetask.DingtalkHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    @Autowired
    private DingtalkHook dingtalkHook;

    @Autowired
    private AiManager aiManager;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/restaurant")
    public String restaurant() {
        return randomRestaurantManager.getRestaurantResult();
    }

    @RequestMapping("/sendDingTalkMsg")
    public String sendDingTalkMsg(@RequestParam String msg) throws IOException {
        dingtalkHook.send(msg);
        return msg;
    }

    @RequestMapping("/repeat")
    public String reply(@RequestBody String body) {
        System.out.println(body);
        return body;
    }

    @RequestMapping("/reply")
    public String ai(@RequestBody String body) throws IOException {
        System.out.println("#1: " + body);
        return aiManager.talk2Ai(body);
    }
}
