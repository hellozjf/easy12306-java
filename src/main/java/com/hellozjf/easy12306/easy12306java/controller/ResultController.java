package com.hellozjf.easy12306.easy12306java.controller;

import com.hellozjf.easy12306.easy12306java.constant.ResultEnum;
import com.hellozjf.easy12306.easy12306java.service.PredictService;
import com.hellozjf.easy12306.easy12306java.service.ResultService;
import com.hellozjf.easy12306.easy12306java.util.ResultUtils;
import com.hellozjf.easy12306.easy12306java.vo.PredictVO;
import com.hellozjf.easy12306.easy12306java.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jingfeng Zhou
 */
@RestController
@Slf4j
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private PredictService predictService;

    @Autowired
    private ResultService resultService;

    /**
     * 接收一个图片文件，返回它的预测结果
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/pic")
    public ResultVO pic(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        // 判断上传过来的文件是不是空的
        if (multipartFile.isEmpty()) {
            return ResultUtils.error(ResultEnum.FILE_CAN_NOT_BE_EMPTY);
        }
        PredictVO predictVO = predictService.predictPic(multipartFile);
        return ResultUtils.success(resultService.getResult(predictVO));
    }

    /**
     * 接收一个图片文件，返回它的预测结果
     *
     * @param base64String
     * @return
     */
    @PostMapping("/base64")
    public ResultVO base64(String base64String) throws Exception {

        // 判断上传过来的文件是不是空的
        if (base64String == null) {
            return ResultUtils.error(ResultEnum.BASE64_FILE_CAN_NOT_BE_NULL);
        }
        PredictVO predictVO = predictService.predictBase64(base64String);
        return ResultUtils.success(resultService.getResult(predictVO));
    }

}
