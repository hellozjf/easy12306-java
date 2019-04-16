package com.hellozjf.easy12306.easy12306java.service.impl;

import com.hellozjf.easy12306.easy12306java.service.ResultService;
import com.hellozjf.easy12306.easy12306java.vo.PictureVO;
import com.hellozjf.easy12306.easy12306java.vo.PredictVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jingfeng Zhou
 */
@Service
@Slf4j
public class ResultServiceImpl implements ResultService {

    /**
     * 从左到右，从上到下，依次的坐标点
     * 两个一组，分别是x和y的坐标
     *
     * 例如第1行，第2列的坐标分别是：offset[0][1][0]和offset[0][1][1]
     */
    private int[][][] offset = {
            {
                    {40, 77},
                    {112, 77},
                    {184, 77},
                    {256, 77},
            },
            {
                    {40, 149},
                    {112, 149},
                    {184, 149},
                    {256, 149},
            },
    };

    @Override
    public String getResult(PredictVO predictVO) {
        List<Integer> integerList = new ArrayList<>();
        for (String question : predictVO.getQuestions()) {
            for (PictureVO pictureVO : predictVO.getPictures()) {
                if (question.equalsIgnoreCase(pictureVO.getDesc())) {
                    int[] pic = offset[pictureVO.getY()][pictureVO.getX()];
                    integerList.add(pic[0]);
                    integerList.add(pic[1]);
                }
            }
        }
        return StringUtils.join(integerList, ",");
    }
}
