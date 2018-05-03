package com.ytzl.itrip.auth.service;

import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.utils.exception.ItripException;

/**
 * Created by sam on 2018/4/23.
 */
public interface TokenService {

    public static final long TOKEN_PROTECTION_PERIOD = 60 * 60 * 1000;

    //客户端标识-USERCODE-USERID-CREATIONDATE-RONDEM[6位]
    //构建Token
    public String generateToken(String userAgent, ItripUser itripUser);

    //保存Token
    public void saveToken(String token, ItripUser itripUser);

    //验证Token是否有效
    public Boolean validateToken(String token, String userAgent);

    //删除Token
    public void removeToken(String token);

    //重置token
    public String retoken(String userAgent, String token) throws ItripException;
}
