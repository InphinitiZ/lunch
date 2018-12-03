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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${dingtalk_access_token}")
    private String ACCESSTOKEN;

    private String WEBHOOK_URL_PREFIX = "https://oapi.dingtalk.com/robot/send?access_token=";

    @Autowired
    private RandomRestaurantManager randomRestaurantManager;

    @Scheduled(cron = "0 08 10 * * MON-FRI")
//    @Scheduled(cron = "*/5 * * * * MON-FRI")
    private void send() throws IOException {
        post(randomRestaurantManager.getRestaurantResult());
    }

    public void send(String msg) throws IOException {
        post(msg);
    }

    private void post(String msg) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(WEBHOOK_URL_PREFIX + ACCESSTOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        DingMessage dingMessage = new DingMessage();
        dingMessage.getText().setContent(msg);

        StringEntity se = new StringEntity(JSON.toJSONString(dingMessage), "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
        }
    }

    public String getACCESSTOKEN() {
        return ACCESSTOKEN;
    }

    public void setACCESSTOKEN(String ACCESSTOKEN) {
        this.ACCESSTOKEN = ACCESSTOKEN;
    }
}
