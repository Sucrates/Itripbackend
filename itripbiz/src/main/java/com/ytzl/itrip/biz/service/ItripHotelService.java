package com.ytzl.itrip.biz.service;

import com.ytzl.itrip.beans.model.ItripAreaDic;
import com.ytzl.itrip.beans.model.ItripHotel;

import java.util.List;
import java.util.Map;

import com.ytzl.itrip.beans.model.ItripLabelDic;
import com.ytzl.itrip.utils.common.Page;


public interface ItripHotelService {

    public ItripHotel getItripHotelById(Long id) throws Exception;

    public List<ItripHotel> getItripHotelListByMap(Map<String, Object> param) throws Exception;

    public Integer getItripHotelCountByMap(Map<String, Object> param) throws Exception;

    public Integer saveItripHotel(ItripHotel itripHotel) throws Exception;

    public Integer modifyItripHotel(ItripHotel itripHotel) throws Exception;

    public Integer removeItripHotelById(Long id) throws Exception;

    public Page<ItripHotel> queryItripHotelPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    //查询酒店特色根据酒店Id
    List<ItripLabelDic> queryItripHotelFeatureByHotelId(int hotelId);

    //根据酒店Id查询商圈
    List<ItripAreaDic> queryItripHotelAreaByHotelId(int hotelId);

}
