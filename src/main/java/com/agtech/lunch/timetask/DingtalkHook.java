package com.agtech.lunch.timetask;

import com.agtech.lunch.dataobject.DingMessage;
import com.agtech.lunch.manager.RandomRestaurantManager;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018-11-30
 * Time: 15:39
 */
@Component
public class DingtalkHook {

    private static final String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=ac85ce7d06b856c9369113665e5022feef73f87917bc17ec1d22e95974acc95b";

    @Autowired
    private RandomRestaurantManager randomRestaurantManager;

//    @Scheduled(cron = "0 50 11 * * MON-FRI")
    @Scheduled(cron = "*/5 * * * * MON-FRI")
    private void send() throws IOException {

        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        String textMsg =randomRestaurantManager.getRestaurantResult();
        DingMessage dingMessage = new DingMessage();
        dingMessage.getText().setContent(textMsg);

        StringEntity se = new StringEntity(JSON.toJSONString(dingMessage), "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
        }
    }
}
