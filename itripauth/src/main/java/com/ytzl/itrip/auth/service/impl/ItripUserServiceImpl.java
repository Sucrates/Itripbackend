package com.ytzl.itrip.auth.service.impl;

import com.ytzl.itrip.auth.service.ItripUserService;
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
        //查询用户根据用户code

        ItripUser itripUser = getItripUserByUserCode(userCode);
        if (null != itripUser) {
            //不等于null   判断用户密码和页面输入密码是否一致（加密）
            String md5UserPassword = DigestUtil.hmacSign(userPassword);
            if (itripUser.getUserPassword().equals(md5UserPassword)) {
                //一致  需要判断用户是否被激活
                if (itripUser.getActivated() == 1) {
                    //被激活  登陆成功返回itripUser
                    return itripUser;
                } else {
                    //未激活  用户未激活
                    throw new ItripException("用户未激活", ErrorCode.AUTH_AUTHENTICATION_FAILED);
                }
            } else {
                //不一致登录失败，用户名或密码错误
                throw new ItripException("用户名或密码错误", ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        } else {
            //如果用户等于null，用户不存在
            throw new ItripException("用户不存在", ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }
    }

    @Override
    public ItripUser getItripUserByUserCode(String userCode) throws ItripException {
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        try {
            List<ItripUser> itripUserList = getItripUserListByMap(param);
            return itripUserList != null && itripUserList.size() > 0 ? itripUserList.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
