package com.ytzl.itrip.auth.service;

import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.utils.exception.ItripException;

/**
 * Created by Su_crates on 2018/4/23.
 */
public interface TokenService {
    public  static final long TOKEN_PROTECTION_PERIOD=60;

    public String generateToken(String userAgent, ItripUser itripUser);

    public void saveToken(String token, ItripUser itripUser);

    public Boolean validateToken(String token, String userAgent);

    public void removeToken(String token);

    public String retoken(String userAgent, String token) throws ItripException;
}
