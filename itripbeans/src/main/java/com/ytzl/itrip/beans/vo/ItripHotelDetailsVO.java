package com.ytzl.itrip.beans.vo;

/**
 * Created by sam on 2018/4/28.
 */
public class ItripHotelDetailsVO {
    //描述
    private String description;
    //名称
    private String name;

    public ItripHotelDetailsVO() {
    }

    public ItripHotelDetailsVO(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
