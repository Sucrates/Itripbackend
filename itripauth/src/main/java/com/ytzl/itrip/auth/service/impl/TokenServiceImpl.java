package com.ytzl.itrip.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.ytzl.itrip.auth.service.TokenService;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.utils.common.*;
import com.ytzl.itrip.utils.exception.ItripException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sam on 2018/4/23.
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisAPI redisAPI;

    @Resource
    private ValidationUtil validationUtil;

    //token:客户端标识-USERCODE-USERID-CREATIONDATE-RONDEM[6位]
    @Override
    public String generateToken(String userAgent, ItripUser itripUser) {
        StringBuffer sbToken = new StringBuffer("token:");
        //拼接客户端标识
        if (UserAgentUtil.checkAgent(userAgent))
            sbToken.append("MOBILE-");
        else
            sbToken.append("PC-");
        //userCode
        String md5UserCode = DigestUtil.hmacSign(itripUser.getUserCode());
        sbToken.append(md5UserCode + "-");
        //userid
        sbToken.append(itripUser.getId() + "-");
        //createTime
        sbToken.append(new SimpleDateFormat("yyyyMMddHHmmss")//
                .format(new Date()) + "-");
        //六位的userAgent加密码
        String md5UserAgent = DigestUtil.hmacSign(userAgent, 6);
        sbToken.append(md5UserAgent);
        //返回拼接结果
        return sbToken.toString();
    }

    @Override
    public void saveToken(String token, ItripUser itripUser) {
        String itripUserJson = JSON.toJSONString(itripUser);
        if (token.indexOf("PC-") != -1)
            redisAPI.set(token, itripUserJson, 60 * 60 * 2);
        else
            redisAPI.set(token, itripUserJson);
    }

    @Override
    public Boolean validateToken(String token, String userAgent) {
        //判断是否存在
        if (!redisAPI.exists(token))
            return false;
        //两次浏览器便是 userAgent完全一致，视为有效
        String tokenUserAgent = token.split("-")[4];
        if (!DigestUtil.hmacSign(userAgent, 6).equals(tokenUserAgent))
            return false;
        return true;
    }

    @Override
    public void removeToken(String token) {
        if (redisAPI.exists(token))
            redisAPI.del(token);
    }

    @Override
    public String retoken(String userAgent, String token) throws ItripException {
        //1、验证Token是否有效
        if (!validateToken(token, userAgent))
            throw new ItripException("未知的token或 token已过期", ErrorCode.AUTH_REPLACEMENT_FAILED);
        // 2、能不能够置换,是否处在保护期
        //时间 1小时
        //token  index3
        try {
            //构建时间
            long genTime = new SimpleDateFormat("yyyyMMddHHmmss")//
                    .parse(token.split("-")[3]).getTime();
            //当前时间
            long currTime = new Date().getTime();
            //当前时间 - token构建时间 > 保护期   可以置换
            if (currTime - genTime < TOKEN_PROTECTION_PERIOD) {
                //禁止替换token
                throw new ItripException("token正处于保护期内，禁止替换", //
                        ErrorCode.AUTH_REPLACEMENT_FAILED);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 3、进行置换（兼容移动端和PC端）
        Long ttl = redisAPI.ttl(token);
        if (ttl > 0 || ttl == -1) {
            //获取用户对象
            ItripUser itripUser = validationUtil.getUser(token);
            //进行替换,生成新的token
            String newToken = generateToken(userAgent, itripUser);
            // 4、老的token延迟过期
            redisAPI.set(token, JSON.toJSONString(itripUser), 60 * 3);
            // 5、新的Token保存到Redis中
            saveToken(newToken, itripUser);
            return newToken;
        } else {
            throw new ItripException("未知的token或 token已过期", ErrorCode.AUTH_REPLACEMENT_FAILED);
        }


    }


}
