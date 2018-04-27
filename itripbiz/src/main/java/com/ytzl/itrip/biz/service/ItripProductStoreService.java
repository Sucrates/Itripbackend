package com.ytzl.itrip.biz.service;
import com.ytzl.itrip.beans.model.ItripProductStore;
import java.util.List;
import java.util.Map;

import com.ytzl.itrip.utils.common.Page;


public interface ItripProductStoreService {

    public ItripProductStore getItripProductStoreById(Long id)throws Exception;

    public List<ItripProductStore>	getItripProductStoreListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripProductStoreCountByMap(Map<String, Object> param)throws Exception;

    public Integer saveItripProductStore(ItripProductStore itripProductStore)throws Exception;

    public Integer modifyItripProductStore(ItripProductStore itripProductStore)throws Exception;

    public Integer removeItripProductStoreById(Long id)throws Exception;

    public Page<ItripProductStore> queryItripProductStorePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
