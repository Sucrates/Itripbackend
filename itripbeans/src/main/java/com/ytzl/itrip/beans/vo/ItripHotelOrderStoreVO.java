package com.ytzl.itrip.beans.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by sam on 2018/5/3.
 */
public class ItripHotelOrderStoreVO {
    //入住时间
    private Date checkInDate;
    //退房时间
    private Date checkOutDate;
    //酒店数量（默认1）
    private Integer count;
    //酒店Id
    private Long hotelId;
    //酒店名称
    private String hotelName;
    //酒店价格
    private Double price;
    //酒店房型Id
    private Long roomId;
    //库存
    private Integer store;

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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }
}
