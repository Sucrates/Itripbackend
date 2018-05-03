package com.ytzl.itrip.biz.service;
import com.ytzl.itrip.beans.model.ItripOrderLinkUser;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Page;


public interface ItripOrderLinkUserService {

    public ItripOrderLinkUser getItripOrderLinkUserById(Long id)throws Exception;

    public List<ItripOrderLinkUser>	getItripOrderLinkUserListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripOrderLinkUserCountByMap(Map<String, Object> param)throws Exception;

    public Integer saveItripOrderLinkUser(ItripOrderLinkUser itripOrderLinkUser)throws Exception;

    public Integer modifyItripOrderLinkUser(ItripOrderLinkUser itripOrderLinkUser)throws Exception;

    public Integer removeItripOrderLinkUserById(Long id)throws Exception;

    public Page<ItripOrderLinkUser> queryItripOrderLinkUserPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;

    Integer removeItripOrderLinkUserByOrderId(Long id);
}
