package com.ytzl.itrip.beans.vo;

/**
 * Created by sam on 2018/5/2.
 */
public class ItripImageVO {

    //图片显示位置
    private Integer position;
    //图片地址
    private String imgUrl;

    public ItripImageVO() {
    }

    public ItripImageVO(Integer position, String imgUrl) {
        this.position = position;
        this.imgUrl = imgUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
