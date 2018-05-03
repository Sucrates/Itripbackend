package com.ytzl.itrip.beans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by sam on 2018/5/3.
 */
@ApiModel(value = "validateRoomStoreVO", description = "酒店预订")
public class ValidateRoomStoreVO {
    //入住时间
    @ApiModelProperty("[必填]，注：接收日期类型 入住时间")
    private Date checkInDate;
    //退房时间
    @ApiModelProperty("[必填]，注：接收日期类型 退房时间")
    private Date checkOutDate;
    //房间数量
    @ApiModelProperty("[必填]，默认请传1")
    private Integer count;
    //酒店Id
    @ApiModelProperty("[必填]，注：接收数字类型 酒店ID ")
    private Long hotelId;
    //房型Id
    @ApiModelProperty("[必填]，注：接收数字类型 房间ID")
    private Long roomId;

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
