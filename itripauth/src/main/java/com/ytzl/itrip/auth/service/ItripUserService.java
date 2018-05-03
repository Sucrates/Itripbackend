package com.ytzl.itrip.auth.service;

import com.ytzl.itrip.beans.model.ItripUser;

import java.util.List;
import java.util.Map;

import com.ytzl.itrip.utils.common.Page;
import com.ytzl.itrip.utils.exception.ItripException;


public interface ItripUserService {

    public ItripUser getItripUserById(Long id) throws Exception;

    public List<ItripUser> getItripUserListByMap(Map<String, Object> param) throws Exception;

    public Integer getItripUserCountByMap(Map<String, Object> param) throws Exception;

    public Integer saveItripUser(ItripUser itripUser) throws Exception;

    public Integer modifyItripUser(ItripUser itripUser) throws Exception;

    public Integer removeItripUserById(Long id) throws Exception;

    public Page<ItripUser> queryItripUserPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    //登录
    public ItripUser login(String userCode, String userPassword) throws ItripException;

    public ItripUser getItripUserByUserCode(String userCode) throws ItripException;

    //根据手机号注册
    public void registerByPhone(ItripUser itripUser) throws ItripException;

    //激活手机号
    public void activateByPhone(String userCode, String code) throws ItripException;

    //激活邮箱
    public void activate(String userCode, String code) throws ItripException;

    //邮箱注册
    public void doregister(ItripUser itripUser) throws ItripException;
}
