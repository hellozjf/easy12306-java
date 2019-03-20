package com.hellozjf.easy12306.easy12306java.controller;

import com.hellozjf.easy12306.easy12306java.constant.ResultEnum;
import com.hellozjf.easy12306.easy12306java.util.ResultUtils;
import com.hellozjf.easy12306.easy12306java.vo.PredictVO;
import com.hellozjf.easy12306.easy12306java.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author Jingfeng Zhou
 */
@RestController
@Slf4j
public class Easy12306Controller {

    /**
     * 接收一个图片文件，返回它的预测结果
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/predict")
    public ResultVO onlineModel(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        // 判断上传过来的文件是不是空的
        if (multipartFile.isEmpty()) {
            return ResultUtils.error(ResultEnum.FILE_CAN_NOT_BE_EMPTY);
        }

        // 获取文件名
        String filename = multipartFile.getOriginalFilename();
        File folder = new File("/pic");
        if (! folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, filename);

        // 将上传上来的文件保存到/pic目录
        try {
            byte[] data = multipartFile.getBytes();
            // 如果待保存的文件夹不存在，那就创建一个文件夹
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 然后把文件保存下来
            IOUtils.copy(new ByteArrayInputStream(data), new FileOutputStream(file));
        } catch (IOException e) {
            log.error("e = {}", e);
            return ResultUtils.error(ResultEnum.FILE_IS_WRONG);
        }

        // 调用脚本预测
        PredictVO predictVO = new PredictVO();
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("shell/predict.sh " + file.getAbsolutePath());
        try (
                InputStream inputStream = process.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                log.debug("{}", line);
                String[] parts = line.split("\\s");
                if (parts.length == 1) {
                    if (predictVO.getQuestion1() == null) {
                        predictVO.setQuestion1(parts[0]);
                    } else {
                        predictVO.setQuestion2(parts[0]);
                    }
                } else {
                    if (parts[0].equalsIgnoreCase("0")) {
                        if (parts[1].equalsIgnoreCase("0")) {
                            predictVO.setPic00(parts[2]);
                        } else if (parts[1].equalsIgnoreCase("1")) {
                            predictVO.setPic01(parts[2]);
                        } else if (parts[1].equalsIgnoreCase("2")) {
                            predictVO.setPic02(parts[2]);
                        } else {
                            predictVO.setPic03(parts[2]);
                        }
                    } else {
                        if (parts[1].equalsIgnoreCase("0")) {
                            predictVO.setPic10(parts[2]);
                        } else if (parts[1].equalsIgnoreCase("1")) {
                            predictVO.setPic11(parts[2]);
                        } else if (parts[1].equalsIgnoreCase("2")) {
                            predictVO.setPic12(parts[2]);
                        } else {
                            predictVO.setPic13(parts[2]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("e = {}", e);
        }

        return ResultUtils.success(predictVO);
    }
}
