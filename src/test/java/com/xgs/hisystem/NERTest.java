package com.xgs.hisystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgs.hisystem.controller.NerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NERTest {
    @Autowired
    private NerController nerController;

    @Test
    public void testNER() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String text = "耳鼻无异常分泌物，乳突无压痛，副鼻窦无压痛，双耳听力正常。口唇红润，口腔黏膜无溃疡、白斑，咽无充血，双侧扁桃体无肿大，舌体无胖大，伸舌居中，无震颤";
        String commands = "/Users/zhangzhiwei/miniconda3/envs/py36/bin/python /Users/zhangzhiwei/Desktop/medical_ner_pytorch/main.py -c predict -s "+text;
        Process proc = null;
        try {
            proc = rt.exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

// read the output from the command
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            if(s.startsWith(" "))
                System.out.println(s);
        }
    }

    @Test
    public void testNer() throws IOException {
        String text = "患者2017-4体检行常规胸部平扫，示“右肺中叶磨玻璃病变，较大者约0.8X1.0cm，性质待查”，建议定期随诊或进一步检查，患者无明显咯血，咳嗽等症状。2018-9因胆囊结石手术就诊当地医院，复查胸CT见右肺中叶结节较前增大，患者2018-12就诊我院门诊复查胸CT提示右肺中叶近叶间裂见磨玻璃密度结节，大小1.4*1.1cm，内见支气管血管穿行，可见空泡征，周围见多发短毛刺，双肺多发微结节（左201im15、21、41，右201im34）。气管支气管通畅，两肺门及纵隔未见明确肿大淋巴结。心影不大。双侧背侧胸膜结节样增厚。胆囊未见明确显示，局部多发高密度影。脾周多发软组织密度结节。右肾中部见类圆形低密度影，平扫CT值10.7HU，直径约3.7cm。与2018-09-25外院老片对比：右肺中叶近斜裂磨玻璃密度结节，范围大致同前，考虑恶性病变-早期肺癌可能。进一步完善PET-CT提示右肺中叶结节轻度代谢增高，SUV0.8，早期肺癌不除外。现为进一步诊治入院检查。";
        String result = nerController.getConstructInfo(text);
        System.out.println(result);
        String[] strings = result.split("},\\{");
        for(int i=0;i<strings.length;i++){
            if(i==0){
                strings[i]+="}";
                continue;
            }
            if(i==strings.length-1){
                strings[i] = "{"+strings[i];
                continue;
            }
            strings[i] = "{"+strings[i]+"}";
        }
        Set<String> words = new HashSet<>();
        Set<String> types = new HashSet<>();
        for(String s:strings){
//            JSONObject object = JSONObject.parseObject(s);
            String s1 = JSON.toJSONString(JSON.parse(s.toString()));
            JSONObject object = JSONObject.parseObject(s1);
            words.add(object.getString("word"));
            types.add(object.getString("type"));
//            {"start":0,"stop":5,"word":"全身皮肤粘膜","type":"BODY"}
//            System.out.println(object.getString("word"));
//            System.out.println(object.getString("type"));
        }
        Iterator<String> wordIt = words.iterator();
        Iterator<String> typeIt = types.iterator();
        while(wordIt.hasNext()){
            System.out.println(wordIt.next());
        }
        System.out.println("------");
        while(typeIt.hasNext()){
            System.out.println(typeIt.next());
        }
    }

}
