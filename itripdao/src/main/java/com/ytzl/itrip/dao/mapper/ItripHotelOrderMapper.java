package com.ytzl.itrip.dao.mapper;

import com.ytzl.itrip.beans.model.ItripHotelOrder;
import com.ytzl.itrip.beans.vo.ItripHotelOrderStoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripHotelOrderMapper {

    public ItripHotelOrder getItripHotelOrderById(@Param(value = "id") Long id) throws Exception;

    public List<ItripHotelOrder> getItripHotelOrderListByMap(Map<String, Object> param) throws Exception;

    public Integer getItripHotelOrderCountByMap(Map<String, Object> param) throws Exception;

    public Integer saveItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception;

    public Integer modifyItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception;

    public Integer removeItripHotelOrderById(@Param(value = "id") Long id) throws Exception;

    //调用库存检查
    void flushStore(Map<String, Object> param);

    //计算库存
    List<ItripHotelOrderStoreVO> queryHotelStore(Map<String, Object> param);
}
