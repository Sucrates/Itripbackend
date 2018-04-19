package com.ytzl.itrip.service;
import com.ytzl.itrip.beans.model.ItripHotelOrder;
import java.util.List;
import java.util.Map;

import com.ytzl.itrip.utils.Page;


public interface ItripHotelOrderService {

    public ItripHotelOrder getItripHotelOrderById(Long id)throws Exception;

    public List<ItripHotelOrder>	getItripHotelOrderListByMap(Map<String,Object> param)throws Exception;

    public Integer getItripHotelOrderCountByMap(Map<String,Object> param)throws Exception;

    public Integer saveItripHotelOrder(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer modifyItripHotelOrder(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer removeItripHotelOrderById(Long id)throws Exception;

    public Page<ItripHotelOrder> queryItripHotelOrderPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception;
}
