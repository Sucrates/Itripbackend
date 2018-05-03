package com.ytzl.itrip.beans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by sam on 2018/4/28.
 */
@ApiModel(value = "searchHotelRoomVO",
        description = "查询酒店房间列表")
public class SearchHotelRoomVO {
    @ApiModelProperty("[必填] 退房日期")
    private String endDate;
    @ApiModelProperty("[必填] 酒店ID")
    private Integer hotelId;
    @ApiModelProperty("[非必填] 是否预订(0:未预定 1:预订)")
    private Integer isBook;
    @ApiModelProperty("[非必填] 是否可取消(0:不可以 1:可以)")
    private Integer isCancel;
    @ApiModelProperty("[非必填] 是否有早餐(0:没有 1:有)")
    private Integer isHavingBreakfast;
    @ApiModelProperty("[非必填] 是否及时响应(0:不及时 1:及时)")
    private Integer isTimelyResponse;
    @ApiModelProperty("[非必填] 支付类型(1:在线付 2:到店付 3:不限) ")
    private Integer payType;
    @ApiModelProperty("[非必填] 床型ID ")
    private Integer roomBedTypeId;
    @ApiModelProperty("[非必填] 入住日期")
    private String startDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getIsBook() {
        return isBook;
    }

    public void setIsBook(Integer isBook) {
        this.isBook = isBook;
    }

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public Integer getIsHavingBreakfast() {
        return isHavingBreakfast;
    }

    public void setIsHavingBreakfast(Integer isHavingBreakfast) {
        this.isHavingBreakfast = isHavingBreakfast;
    }

    public Integer getIsTimelyResponse() {
        return isTimelyResponse;
    }

    public void setIsTimelyResponse(Integer isTimelyResponse) {
        this.isTimelyResponse = isTimelyResponse;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getRoomBedTypeId() {
        return roomBedTypeId;
    }

    public void setRoomBedTypeId(Integer roomBedTypeId) {
        this.roomBedTypeId = roomBedTypeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
