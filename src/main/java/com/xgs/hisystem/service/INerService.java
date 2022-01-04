package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;

import java.io.IOException;
import java.util.List;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
public interface INerService {
    String getConstructInfo(String text) throws IOException;


}
