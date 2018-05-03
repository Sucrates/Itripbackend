package com.ytzl.itrip.beans.vo;

/**
 * Created by sam on 2018/5/2.
 */
public class ItripCommentScoreVO {
    //设施评分
    private Double avgFacilitiesScore;
    //卫生评分
    private Double avgHygieneScore;
    //位置评分
    private Double avgPositionScore;
    //总评分
    private Double avgScore;
    //服务评分
    private Double avgServiceScore;

    public Double getAvgFacilitiesScore() {
        return avgFacilitiesScore;
    }

    public void setAvgFacilitiesScore(Double avgFacilitiesScore) {
        this.avgFacilitiesScore = avgFacilitiesScore;
    }

    public Double getAvgHygieneScore() {
        return avgHygieneScore;
    }

    public void setAvgHygieneScore(Double avgHygieneScore) {
        this.avgHygieneScore = avgHygieneScore;
    }

    public Double getAvgPositionScore() {
        return avgPositionScore;
    }

    public void setAvgPositionScore(Double avgPositionScore) {
        this.avgPositionScore = avgPositionScore;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Double getAvgServiceScore() {
        return avgServiceScore;
    }

    public void setAvgServiceScore(Double avgServiceScore) {
        this.avgServiceScore = avgServiceScore;
    }
}
