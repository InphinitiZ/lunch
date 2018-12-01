package com.agtech.lunch.manager;

import com.agtech.lunch.dataobject.DingMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AiManager {

    private String AI_URL_PREFIX = "http://openapi.tuling123.com/openapi/api/v2";
    private String testSentence = "{\"reqType\":0,\"perception\":{\"inputText\":{\"text\":\" 哈 哈哈 \"}},\"userInfo\":{\"apiKey\":\"72d90ba1126148dca911d90c2fcb2268\",\"userId\":\"359097\"}}";

    public String talk2Ai(String msg) throws IOException {
        JSONObject msgJsonObj = JSON.parseObject(msg);
        String sentence = msgJsonObj.getJSONObject("text").getString("content");

        System.out.println("#2:" + sentence);

        JSONObject send2AiJsonObj = JSON.parseObject(testSentence);
        send2AiJsonObj.getJSONObject("perception").getJSONObject("inputText").put("text", sentence);

        System.out.println("#3: " + send2AiJsonObj.toJSONString());


        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(AI_URL_PREFIX);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        StringEntity se = new StringEntity(JSON.toJSONString(send2AiJsonObj), "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result = EntityUtils.toString(response.getEntity(), "utf-8");

            System.out.println("#4: " + result);

            JSONObject resultJsonObj = JSON.parseObject(result);
            JSONArray results = resultJsonObj.getJSONArray("results");
            JSONObject replyJsonObj = (JSONObject) results.get(0);
            String reply = replyJsonObj.getJSONObject("values").getString("text");
            System.out.println("#5: " + reply);

            DingMessage dingMessage = new DingMessage();
            dingMessage.getText().setContent(reply);
            return JSON.toJSONString(dingMessage);
        }

        return "不懂";
    }
}
