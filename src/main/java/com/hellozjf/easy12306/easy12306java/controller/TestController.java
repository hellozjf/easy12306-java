package com.hellozjf.easy12306.easy12306java.controller;

import com.hellozjf.easy12306.easy12306java.util.ResultUtils;
import com.hellozjf.easy12306.easy12306java.vo.ResultVO;
import com.hellozjf.easy12306.easy12306java.vo.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jingfeng Zhou
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public ResultVO test() {
        TestVO testVO = new TestVO();
        testVO.setIndexes(new Integer[] {1, 2, 3});
        testVO.setStrings(new String[] {"a1", "b22", "c333"});
        return ResultUtils.success(testVO);
    }
}
