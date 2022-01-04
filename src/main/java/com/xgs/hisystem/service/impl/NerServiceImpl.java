package com.xgs.hisystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xgs.hisystem.common.constant.HisConstants;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.IGetPatientInfoService;
import com.xgs.hisystem.service.INerService;
import com.xgs.hisystem.service.IOutpatientService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class NerServiceImpl implements INerService {


    @Override
    public String getConstructInfo(String text) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String commands = "/Users/zhangzhiwei/miniconda3/envs/py36/bin/python /Users/zhangzhiwei/Desktop/medical_ner_pytorch/main.py -c predict -s "+text;
        Process proc = null;
        try {
            proc = rt.exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

// read the output from the command
        String s;
        String result = null;
        while ((s = stdInput.readLine()) != null) {
            if(s.startsWith(" "))
                result+=s;
        }
        String sentences = result.replaceAll(" ", "").substring(4);
        String[] strs = sentences.split(",");
//        {"start":0,"stop":5,"word":"全身皮肤粘膜","type":"BODY"}
        return sentences;
    }
}
