package com.ytzl.itrip.auth.service.impl;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.ytzl.itrip.auth.service.SMSMessageService;
import com.ytzl.itrip.utils.common.ErrorCode;
import com.ytzl.itrip.utils.exception.ItripException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by sam on 2018/4/24.
 */
@Service("sMSMessageService")
public class SMSMessageServiceImpl implements SMSMessageService {

    @Value("#{itrip.sms_serverIP}")
    private String serverIP;
    @Value("#{itrip.sms_serverPort}")
    private String serverPort;
    @Value("#{itrip.sms_accountSid}")
    private String accountSid;
    @Value("#{itrip.sms_accountToken}")
    private String accountToken;
    @Value("#{itrip.sms_appId}")
    private String appId;


    @Override
    public void send(String to, String templateId, String[] datas) throws ItripException {
        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(serverIP, serverPort);
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        restAPI.setAccount(accountSid,
                accountToken);
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        restAPI.setAppId(appId);
        // 请使用管理控制台中已创建应用的APPID。
        result = restAPI.sendTemplateSMS(to, templateId, datas);
        System.out.println("SDKTestGetSubAccounts result=" + result);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            throw new ItripException("短信发送失败，请稍后再试", ErrorCode.AUTH_UNKNOWN);
        }
    }
}
