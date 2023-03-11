package com.cj.serviceoss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    private  String bucketname;

//    @Value("${aliyun.oss.file.filehost}")
//    private String file_host;

    public static String ENDPOINT;
    public static String KEYID;
    public static String KEYSECRET;
    public static String BUCKETNAME;
    public static String FILE_HOST;
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT=endpoint;
        KEYID=keyid;
        KEYSECRET=keysecret;
        BUCKETNAME=bucketname;
//        FILE_HOST=file_host;
    }
}
