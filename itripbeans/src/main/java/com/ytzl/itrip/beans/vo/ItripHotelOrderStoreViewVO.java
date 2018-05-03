package com.ytzl.itrip.beans.vo;

import java.util.Date;

/**
 * Created by sam on 2018/5/3.
 */
public class ItripHotelOrderStoreViewVO {

    //房型Id
    private Long roomId;

    //预订时间
    private Date recordDate;

    //库存
    private Integer store;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }
}
