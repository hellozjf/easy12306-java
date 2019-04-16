package com.hellozjf.easy12306.easy12306java.service;

import com.hellozjf.easy12306.easy12306java.vo.PredictVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jingfeng Zhou
 */
public interface PredictService {
    PredictVO predictPic(MultipartFile multipartFile) throws Exception;
    PredictVO predictBase64(String base64String) throws Exception;
}
