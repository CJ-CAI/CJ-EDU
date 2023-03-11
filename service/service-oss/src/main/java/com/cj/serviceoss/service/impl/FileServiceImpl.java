package com.cj.serviceoss.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.model.CannedAccessControlList;
import com.cj.servicebase.exception.GuliException;
import com.cj.serviceoss.service.FileService;
import com.cj.serviceoss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String   upload(MultipartFile file,String file_type) {
        String endPoint = ConstantPropertiesUtil.ENDPOINT;
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        String bucketName = ConstantPropertiesUtil.BUCKETNAME;
//        String fileHost = ConstantPropertiesUtil.FILE_HOST;
        String uploadUrl = null;
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //构建日期路径：avatar/2019/02/26/文件名
            String fileDate = new DateTime().toString("yyyy-MM-dd");
            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = file_type+"/"+fileDate + "/" + newName;
            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址
            uploadUrl = "http://" + bucketName + "." + endPoint + "/" + fileUrl;
        } catch (IOException e) {
            throw new GuliException();
        }
        return uploadUrl;
    }

    @Override
    public Boolean delete(String objectName) {
        objectName=objectName.replace("https://guli-edu-0114.oss-cn-hangzhou.aliyuncs.com/","");
        objectName=objectName.replace("http://guli-edu-0114.oss-cn-hangzhou.aliyuncs.com/","");
        String endPoint = ConstantPropertiesUtil.ENDPOINT;
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        String bucketName = ConstantPropertiesUtil.BUCKETNAME;

        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
//        System.out.println("删除文件"+objectName);
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketName, objectName);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return true;
    }
}
