package com.ytzl.itrip.service;
import com.ytzl.itrip.beans.model.ItripUserLinkUser;
import java.util.List;
import java.util.Map;

import com.ytzl.itrip.utils.Page;


public interface ItripUserLinkUserService {

    public ItripUserLinkUser getItripUserLinkUserById(Long id)throws Exception;

    public List<ItripUserLinkUser>	getItripUserLinkUserListByMap(Map<String,Object> param)throws Exception;

    public Integer getItripUserLinkUserCountByMap(Map<String,Object> param)throws Exception;

    public Integer saveItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    public Integer modifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    public Integer removeItripUserLinkUserById(Long id)throws Exception;

    public Page<ItripUserLinkUser> queryItripUserLinkUserPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception;
}
