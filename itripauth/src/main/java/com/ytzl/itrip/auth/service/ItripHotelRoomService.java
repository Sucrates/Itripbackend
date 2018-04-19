package com.ytzl.itrip.auth.service;
import com.ytzl.itrip.beans.model.ItripHotelRoom;
import java.util.List;
import java.util.Map;

import com.ytzl.itrip.utils.Page;


public interface ItripHotelRoomService {

    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception;

    public List<ItripHotelRoom>	getItripHotelRoomListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripHotelRoomCountByMap(Map<String, Object> param)throws Exception;

    public Integer saveItripHotelRoom(ItripHotelRoom ItripHotelRoom)throws Exception;

    public Integer modifyItripHotelRoom(ItripHotelRoom ItripHotelRoom)throws Exception;

    public Integer removeItripHotelRoomById(Long id)throws Exception;

    public Page<ItripHotelRoom> queryItripHotelRoomPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
