package com.example.spba.controller;

import com.example.spba.utils.AsyncTask;
import com.example.spba.utils.QiniuUtil;
import com.example.spba.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@RestController
public class CommonController
{

    @Autowired
    private AsyncTask asyncTask;

    /**
     * 上传图片
     * @param file
     * @param type 图片分类：public公用图片 avatar头像 等等
     * @return
     * @throws IOException
     */
    @PostMapping("/image/upload")
    public R imageUpload(MultipartFile file,
                         @RequestParam(name = "type", defaultValue = "public") String type) throws IOException
    {
        if (file == null) {
            return R.error("请上传图片");
        }
        String[] limit = {"image/jpeg", "image/jpg", "image/png", "image/gif"};

        String imageType = file.getContentType();
        if (!Arrays.asList(limit).contains(imageType)) {
            return R.error("请上传jpeg|png|gif中任意一种格式的图片");
        }

        // 上传图片至七牛云
        String path = QiniuUtil.uploadImageByFile((FileInputStream) file.getInputStream(), type);
        if (path == null) {
            return R.error("上传失败，请稍后重试");
        }

        HashMap data = new HashMap<>();
        data.put("path", path);

        return R.success(data);
    }

    /**
     * 获取七牛token，用于前端vue上传视频至七牛云
     * @return
     */
    @GetMapping("/qiniu/token")
    public HashMap getQiniuToken()
    {
        HashMap data = new HashMap<>();
        data.put("uptoken", QiniuUtil.getToken());

        return data;
    }

    /**
     * 异步任务测试方法1
     * @return
     */
    @GetMapping("/test1")
    public R asyncTest1()
    {
        asyncTask.test1();
        System.out.println("异步任务测试方法1");
        return R.success();
    }

    /**
     * 异步任务测试方法2（多线程执行）
     * @return
     */
    @GetMapping("/test2")
    public R asyncTest2()
    {
        Integer i = 0;
        while (i < 100) {
            asyncTask.test2(i.toString());
            i++;
        }
        return R.success();
    }
}
