package com.ytzl.itrip.auth.service;

import com.ytzl.itrip.utils.exception.ItripException;

/**
 * Created by Su_crates on 2018/4/25.
 */
public interface MailService {
    public void send(String to, String code) throws ItripException;


}
