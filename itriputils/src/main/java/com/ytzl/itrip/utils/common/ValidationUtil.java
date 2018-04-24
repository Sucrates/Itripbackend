package com.ytzl.itrip.utils.common;

import com.alibaba.fastjson.JSON;
import com.ytzl.itrip.beans.model.ItripUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by sam on 2018/4/23.
 */
@Component
public class ValidationUtil {

    @Resource
    private RedisAPI redisAPI;

    /**
     * 通过Token获取用户信息
     *
     * @param token
     * @return
     */
    public ItripUser getUser(String token) {
        try {
            //判断token是否存在
            if (!redisAPI.exists(token))
                return null;
            String itripUserJson = redisAPI.get(token);
            ItripUser itripUser = JSON.parseObject(itripUserJson, ItripUser.class);
            return itripUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
