package com.ytzl.itrip.auth.service.impl;

import com.ytzl.itrip.auth.service.MailService;
import com.ytzl.itrip.utils.exception.ItripException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by sam on 2018/4/25.
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Resource
    private SimpleMailMessage simpleMailMessage;

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void send(String to, String code) throws ItripException {
        //发送给谁
        simpleMailMessage.setTo(to);
        //邮件内容
        simpleMailMessage.setText("您好,您的邮箱激活码为:" + code
                + ",请在三分钟内完成激活!");
        //发送邮件
        javaMailSender.send(simpleMailMessage);
    }
}
