package com.ytzl.itrip.beans.vo;

/**
 * Created by sam on 2018/5/2.
 */
public class ItripCommentCountVO {
    //有待改善
    private Integer improve;
    //值得推荐
    private Integer isok;
    //有图片
    private Integer havingimg;
    //所有
    private Integer allcomment;

    public ItripCommentCountVO() {
    }

    public ItripCommentCountVO(Integer improve, Integer isok, Integer havingimg, Integer allcomment) {
        this.improve = improve;
        this.isok = isok;
        this.havingimg = havingimg;
        this.allcomment = allcomment;
    }

    public Integer getImprove() {
        return improve;
    }

    public void setImprove(Integer improve) {
        this.improve = improve;
    }

    public Integer getIsok() {
        return isok;
    }

    public void setIsok(Integer isok) {
        this.isok = isok;
    }

    public Integer getHavingimg() {
        return havingimg;
    }

    public void setHavingimg(Integer havingimg) {
        this.havingimg = havingimg;
    }

    public Integer getAllcomment() {
        return allcomment;
    }

    public void setAllcomment(Integer allcomment) {
        this.allcomment = allcomment;
    }
}
