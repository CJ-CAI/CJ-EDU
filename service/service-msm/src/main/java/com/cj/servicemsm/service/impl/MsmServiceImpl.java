package com.cj.servicemsm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.tea.TeaException;
import com.cj.servicemsm.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId("LTAI5tJwRoBxFbiNpwzCceVK")
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret("U3ZcJP1do1uTbkv7XQKLVA54f2wbV8");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
    @Override
    public boolean send(String PhoneNumber,Map<String,Object> param) throws Exception {

        com.aliyun.dysmsapi20170525.Client client = MsmServiceImpl.createClient("accessKeyId", "accessKeySecret");
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setSignName("蔡江在线教育网站")
                .setTemplateCode("SMS_269550264")
                .setTemplateParam(JSONObject.toJSONString(param))
                .setPhoneNumbers(PhoneNumber);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return true;
    }
}
