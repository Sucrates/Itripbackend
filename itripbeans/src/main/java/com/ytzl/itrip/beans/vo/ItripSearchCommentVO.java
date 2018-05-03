package com.ytzl.itrip.beans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sam on 2018/5/2.
 */
@ApiModel(value = "itripSearchCommentVO", description = "查询酒店评论列表")
public class ItripSearchCommentVO {

    @ApiModelProperty(" [必填] 酒店ID ")
    private Long hotelId;

    @ApiModelProperty(" [必填，注：接收数字类型] 是否有评论照片（0 无图片 1 有图片 -1 不限）")
    private Integer isHavingImg;

    @ApiModelProperty("[必填，注：接收数字类型] 是否满意（0：有待改善 1：值得推荐  -1 不限）")
    private Integer isOk;

    @ApiModelProperty("[必填] 页码 ")
    private Integer pageNo;

    @ApiModelProperty("[必填] 页面容量")
    private Integer pageSize;


    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getIsHavingImg() {
        return isHavingImg;
    }

    public void setIsHavingImg(Integer isHavingImg) {
        this.isHavingImg = isHavingImg;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
