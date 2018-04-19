package com.ytzl.itrip.auth.controller;

import com.ytzl.itrip.auth.service.ItripHotelRoomService;
import com.ytzl.itrip.beans.model.ItripHotelRoom;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2018/4/17.
 */

@Controller
@RequestMapping("/hotelRoom")
public class ItripHotelRoomController {

    @Resource
    private ItripHotelRoomService itripHotelRoomService;

    //查询全部
    @RequestMapping("/queryAll")
    @ResponseBody
    public List<ItripHotelRoom> queryAll() throws Exception {
        Map<String, Object> map = new HashMap<>();
        return itripHotelRoomService.getItripHotelRoomListByMap(map);
    }

}
