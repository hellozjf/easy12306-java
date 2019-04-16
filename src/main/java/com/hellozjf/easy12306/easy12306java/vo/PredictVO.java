package com.hellozjf.easy12306.easy12306java.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jingfeng Zhou
 */
@Data
public class PredictVO {
    private List<String> questions;
    private List<PictureVO> pictures;

    public PredictVO() {
        questions = new ArrayList<>();
        pictures = new ArrayList<>();
    }
}
