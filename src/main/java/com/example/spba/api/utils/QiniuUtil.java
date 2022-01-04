package com.example.spba.api.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileInputStream;

@Component
public class QiniuUtil
{

    private static String bucketName;
    private static String accessKey;
    private static String secretKey;

    @Value("${spba.qiniu.bucket-name}")
    public void setBucketName(String value) { bucketName = value; }

    @Value("${spba.qiniu.access-key}")
    public void setAccessKey(String value) { accessKey = value; }

    @Value("${spba.qiniu.secret-key}")
    public void setSecretKey(String value) { secretKey = value; }

    private static RedisUtil redisUtil;

    @Resource
    public void setRedisTemplate(RedisUtil redisUtil) {
        QiniuUtil.redisUtil = redisUtil;
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken()
    {
        String tokenKey = "spba:qiniu:token";
        Object tokenObj = redisUtil.get(tokenKey);
        if (tokenObj != null) {
            return tokenObj.toString();
        } else {
            Auth auth = Auth.create(accessKey, secretKey);
            String tokenVal = auth.uploadToken(bucketName);
            if (tokenVal != null) {
                redisUtil.set(tokenKey, tokenVal, 3500);
            }
            return tokenVal;
        }
    }

    /**
     * 上传图片
     * 数据流上传
     * @param file
     * @param namespace
     * @return
     */
    public static String uploadImageByFile(FileInputStream file, String namespace)
    {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Region.region1());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证
        String upToken = getToken();
        // 生成key
        String key = getImagePath(namespace);
        try {
            Response response = uploadManager.put(file, key, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            return null;
        }
    }

    /**
     * 上传图片
     * 直接指定文件的完整路径即可
     * @param localFilePath
     * @param namespace
     * @return
     */
    public static String uploadImageByPath(String localFilePath, String namespace)
    {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Region.region1());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证
        String upToken = getToken();
        // 生成key
        String key = getImagePath(namespace);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            return null;
        }
    }

    /**
     * 生成图片存储路径
     * @param namespace 文件夹名称
     * @return
     */
    public static String getImagePath(String namespace)
    {
        String date = Time.getNowTimeDate("yyyyMMdd");
        Long time = System.currentTimeMillis();
        String path = "spba/image/" + namespace + "/" + date + time + ".png";
        return path;
    }
}
