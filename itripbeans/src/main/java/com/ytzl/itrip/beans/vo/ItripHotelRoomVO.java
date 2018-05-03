package com.ytzl.itrip.beans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sam on 2018/4/20.
 */
@ApiModel(value = "itripHotelRoomVO", description = "酒店房间业务模块VO")
public class ItripHotelRoomVO {
    //酒店ID
    @ApiModelProperty(value = "[必填]酒店Id", required = true)
    private Long hotelId;


    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}
