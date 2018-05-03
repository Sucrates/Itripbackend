package com.ytzl.itrip.beans.vo;

/**
 * Created by sam on 2018/4/23.
 */
public class ItripUserTokenVO {
    //过期时间
    private Long expTime;
    //构建时间
    private Long genTime;
    //token
    private String token;

    public ItripUserTokenVO() {

    }

    public ItripUserTokenVO(String token, Long expTime, Long genTime) {
        this.expTime = expTime;
        this.genTime = genTime;
        this.token = token;
    }

    public Long getExpTime() {
        return expTime;
    }

    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }

    public Long getGenTime() {
        return genTime;
    }

    public void setGenTime(Long genTime) {
        this.genTime = genTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
