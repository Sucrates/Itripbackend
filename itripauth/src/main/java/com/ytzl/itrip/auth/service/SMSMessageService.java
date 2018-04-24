package com.ytzl.itrip.auth.service;

import com.ytzl.itrip.utils.exception.ItripException;

/**
 * Created by sam on 2018/4/24.
 */
public interface SMSMessageService {

    //发送短信
    public void send(String to, String templateId, String[] datas) throws ItripException;
}
