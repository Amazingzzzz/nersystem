package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.service.INerService;
import com.xgs.hisystem.service.IOutpatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/ner")
@Api(tags = "命名实体识别API")
public class NerController {

    @Autowired
    private INerService nerService;

    /**
     * 将病历文本转化成结构化文本
     *
     * @return
     */
    @PostMapping(value = "/getConstructInfo")
    public String getConstructInfo(@RequestParam String nerText) throws IOException {
        return nerService.getConstructInfo(nerText);
    }
}
