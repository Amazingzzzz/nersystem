package com.xgs.hisystem.hanlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class Text {
    public static void main(String[] args) {
        //请求头中的token
//        String token="5c055a2c98f04dfa93db971570efb6fb1641265754966token";
        String token="f1f085c743c54070b7fc39b914774dba1641266661618token";
        //申请的接口地址
//        String url="http://comdo.hanlp.com/hanlp/v1/keyword/extract";
        String url="http://comdo.hanlp.com/hanlp/v1/dependency/dependency";
        //所有参数
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", "头颅大小正常无畸形，无压痛、肿块、结节。眼睑无水肿、下垂，睑结膜无充血、" +
                "出血、苍白、水肿，巩膜无黄染，双侧瞳孔等大正圆，对光反射灵敏。耳鼻无异常分泌物，乳突无压痛，副鼻窦无压痛，双耳听力正常。");
//        params.put("size", "8");
        //执行api
        String result=doHanlpApi(token,url,params);
        System.out.println(result);
    }
    public static String doHanlpApi(String token,String url,Map<String,Object> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加header请求头，token请放在header里
            httpPost.setHeader("token", token);
            // 创建参数列表
            List<NameValuePair> paramList = new ArrayList<>();
            if (params != null) {
                for (String key : params.keySet()) {
                    //所有参数依次放在paramList中
                    paramList.add(new BasicNameValuePair(key, (String) params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(response!=null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}