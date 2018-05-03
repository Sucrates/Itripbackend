package com.ytzl.itrip.beans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sam on 2018/5/3.
 */
@ApiModel(value = "itripSearchUserLinkUserVO", description = "查询常用联系人信息")
public class ItripSearchUserLinkUserVO {

    @ApiModelProperty(" [必填] 常用联系人姓名")
    private String linkUserName;

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }
}
