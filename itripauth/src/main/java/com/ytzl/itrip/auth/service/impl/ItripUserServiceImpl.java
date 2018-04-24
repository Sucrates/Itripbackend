package com.ytzl.itrip.auth.service.impl;

import com.ytzl.itrip.auth.service.ItripUserService;
import com.ytzl.itrip.auth.service.SMSMessageService;
import com.ytzl.itrip.dao.mapper.ItripUserMapper;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.utils.common.*;
import com.ytzl.itrip.utils.exception.ItripException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("itripUserService")
public class ItripUserServiceImpl implements ItripUserService {

    @Resource
    private ItripUserMapper itripUserMapper;

    @Resource
    private RedisAPI redisAPI;

    @Resource
    private SMSMessageService smsMessageService;

    public ItripUser getItripUserById(Long id) throws Exception {
        return itripUserMapper.getItripUserById(id);
    }

    public List<ItripUser> getItripUserListByMap(Map<String, Object> param) throws Exception {
        return itripUserMapper.getItripUserListByMap(param);
    }

    public Integer getItripUserCountByMap(Map<String, Object> param) throws Exception {
        return itripUserMapper.getItripUserCountByMap(param);
    }

    public Integer saveItripUser(ItripUser itripUser) throws Exception {
        itripUser.setCreationDate(new Date());
        return itripUserMapper.saveItripUser(itripUser);
    }

    public Integer modifyItripUser(ItripUser itripUser) throws Exception {
        itripUser.setModifyDate(new Date());
        return itripUserMapper.modifyItripUser(itripUser);
    }

    public Integer removeItripUserById(Long id) throws Exception {
        return itripUserMapper.removeItripUserById(id);
    }

    public Page<ItripUser> queryItripUserPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripUserMapper.getItripUserCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUser> itripUserList = itripUserMapper.getItripUserListByMap(param);
        page.setRows(itripUserList);
        return page;
    }

    @Override
    public ItripUser login(String userCode, String userPassword) throws ItripException {
        //查询用户根据用户Code
        ItripUser itripUser = getItripUserByUserCode(userCode);
        if (null != itripUser) {
            //不等于空 -->  判断用户密码和页面输入密码是否一致 （加密）
            //加密页面输入的密码
            String md5UserPassword = DigestUtil.hmacSign(userPassword);
            if (itripUser.getUserPassword().equals(md5UserPassword)) {
                //一致  需要判断用户是否被激活 ==1 被激活 0未激活
                if (itripUser.getActivated() == 1) {
                    //被激活 登录成功返回itripUser
                    return itripUser;
                } else {
                    //未被激活，用户未激活
                    throw new ItripException("用户未激活", ErrorCode.AUTH_AUTHENTICATION_FAILED);
                }
            } else {
                //不一致登录失败， 用户名或密码错误
                throw new ItripException("用户名或密码错误", ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        } else {
            //如果用户等于null，用户不存在
            throw new ItripException("用户不存在", ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }
    }

    @Override
    public ItripUser getItripUserByUserCode(String userCode) throws ItripException {
        Map<String, Object> param = new HashMap();
        param.put("userCode", userCode);
        try {
            List<ItripUser> itripUserList = getItripUserListByMap(param);
            return itripUserList != null && itripUserList.size() > 0 //
                    ? itripUserList.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void registerByPhone(ItripUser itripUser) throws ItripException {
        //添加用户到数据库
        try {
            //未激活状态
            itripUser.setActivated(0);
            saveItripUser(itripUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ItripException("注册用户异常，请稍后再试!",
                    ErrorCode.AUTH_UNKNOWN);
        }
        //生成短信验证码
        int code = DigestUtil.randomCode();
        //发送短信验证码
        smsMessageService.send(itripUser.getUserCode(),
                "1",
                new String[]{"" + code, "三"});
        //保存短信验证码，时间为三分钟
        redisAPI.set("activation:" + itripUser.getUserCode(),
                "" + code, 60 * 3);
    }

    @Override
    public void activateByPhone(String userCode, String code) throws ItripException {
        //获取redis中短信验证码
        String rCode = redisAPI.get("activation:" + userCode);
        //判断和用户输入是否一致
        if (rCode.equals(code)) {
            //一致则更新用户信息
            //通过userCode查询用户信息
            ItripUser itripUser = getItripUserByUserCode(userCode);
            //已激活
            itripUser.setActivated(1);
            itripUser.setFlatID(itripUser.getId());
            itripUser.setUserType(0);
            try {
                modifyItripUser(itripUser);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ItripException("激活用户异常，请稍后再试!",
                        ErrorCode.AUTH_UNKNOWN);
            }
        } else {
            //否则通知验证码错误
            throw new ItripException("验证失败");
        }
    }

}
