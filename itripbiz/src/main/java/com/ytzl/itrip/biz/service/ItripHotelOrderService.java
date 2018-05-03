package com.ytzl.itrip.biz.service;

import com.ytzl.itrip.beans.model.ItripHotelOrder;

import java.util.List;
import java.util.Map;

import com.ytzl.itrip.beans.model.ItripUserLinkUser;
import com.ytzl.itrip.beans.vo.ItripHotelOrderStoreVO;
import com.ytzl.itrip.utils.common.Page;


public interface ItripHotelOrderService {

    public ItripHotelOrder getItripHotelOrderById(Long id) throws Exception;

    public List<ItripHotelOrder> getItripHotelOrderListByMap(Map<String, Object> param) throws Exception;

    public Integer getItripHotelOrderCountByMap(Map<String, Object> param) throws Exception;

    public Map<String,Object> saveItripHotelOrder(ItripHotelOrder itripHotelOrder, List<ItripUserLinkUser> linkUserList) throws Exception;

    public Integer modifyItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception;

    public Integer removeItripHotelOrderById(Long id) throws Exception;

    public Page<ItripHotelOrder> queryItripHotelOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;


    //查询酒店库存
    public List<ItripHotelOrderStoreVO> queryHotelStore(Map<String, Object> param) throws Exception;


    //检查库存是否充足
    Boolean validateHotelStore(Map<String, Object> param);
}
