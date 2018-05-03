package com.ytzl.itrip.beans.vo;

import java.util.List;

/**
 * Created by sam on 2018/4/28.
 */
public class ItripHotelVodeoDescVO {

    //酒店名称
    private String hotelName;

    //酒店特色
    private List<String> hotelFeatureList;
    //酒店区域
    private List<String> tradingAreaNameList;

    public ItripHotelVodeoDescVO() {
    }

    public ItripHotelVodeoDescVO(String hotelName, List<String> hotelFeatureList, List<String> tradingAreaNameList) {
        this.hotelName = hotelName;
        this.hotelFeatureList = hotelFeatureList;
        this.tradingAreaNameList = tradingAreaNameList;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<String> getHotelFeatureList() {
        return hotelFeatureList;
    }

    public void setHotelFeatureList(List<String> hotelFeatureList) {
        this.hotelFeatureList = hotelFeatureList;
    }

    public List<String> getTradingAreaNameList() {
        return tradingAreaNameList;
    }

    public void setTradingAreaNameList(List<String> tradingAreaNameList) {
        this.tradingAreaNameList = tradingAreaNameList;
    }
}
