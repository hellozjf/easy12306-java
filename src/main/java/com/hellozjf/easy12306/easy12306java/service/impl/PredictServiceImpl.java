package com.hellozjf.easy12306.easy12306java.service.impl;

import com.hellozjf.easy12306.easy12306java.config.ScriptConfig;
import com.hellozjf.easy12306.easy12306java.service.PredictService;
import com.hellozjf.easy12306.easy12306java.util.ImageUtils;
import com.hellozjf.easy12306.easy12306java.util.UUIDUtils;
import com.hellozjf.easy12306.easy12306java.vo.PictureVO;
import com.hellozjf.easy12306.easy12306java.vo.PredictVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author Jingfeng Zhou
 */
@Service
@Slf4j
public class PredictServiceImpl implements PredictService {

    @Autowired
    private ScriptConfig scriptConfig;

    @Override
    public PredictVO predictPic(MultipartFile multipartFile) throws Exception {

        // 获取文件名
        String filename = UUIDUtils.genId() + ".jpg";
        File folder = new File("/pic");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, filename);

        // 将上传上来的文件保存到/pic目录
        byte[] data = multipartFile.getBytes();
        // 如果待保存的文件夹不存在，那就创建一个文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 然后把文件保存下来
        IOUtils.copy(new ByteArrayInputStream(data), new FileOutputStream(file));

        // 调用脚本预测
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(scriptConfig.getPath() + " " + file.getAbsolutePath());
        return predictProcess(process);
    }

    @Override
    public PredictVO predictBase64(String base64String) throws Exception {

        // 获取文件名
        String filename = UUIDUtils.genId() + ".jpg";
        File folder = new File("/pic");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, filename);

        // 将上传上来的文件保存到/pic目录
        byte[] data = ImageUtils.decode(base64String);
        // 如果待保存的文件夹不存在，那就创建一个文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 然后把文件保存下来
        IOUtils.copy(new ByteArrayInputStream(data), new FileOutputStream(file));

        // 调用脚本预测
        PredictVO predictVO = new PredictVO();
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(scriptConfig.getPath() + " " + file.getAbsolutePath());
        return predictProcess(process);
    }

    /**
     * 通过easy12306程序的执行结果获取预测结果
     *
     * 预测的结果可能为：
     * 电子秤
     * 风铃
     * 0 0 电子秤
     * 0 1 绿豆
     * 0 2 蒸笼
     * 0 3 蒸笼
     * 1 0 风铃
     * 1 1 电子秤
     * 1 2 网球拍
     * 1 3 网球拍
     *
     * @param process
     * @return
     */
    private PredictVO predictProcess(Process process) {
        try (
                InputStream inputStream = process.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            PredictVO predictVO = new PredictVO();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                log.debug("{}", line);
                String[] parts = line.split("\\s");
                if (parts.length == 1) {
                    // 这里可能会添加一个或两个问题
                    predictVO.getQuestions().add(parts[0]);
                } else {
                    // 这里会添加从左到右，从上到下的图片描述
                    PictureVO pictureVO = new PictureVO(Integer.valueOf(parts[1]), Integer.valueOf(parts[0]), parts[2]);
                    predictVO.getPictures().add(pictureVO);
                }
            }
            return predictVO;
        } catch (Exception e) {
            log.error("e = {}", e);
            return null;
        }
    }
}
