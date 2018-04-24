package com.ytzl.itrip.beans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sam on 2018/4/24.
 */
@ApiModel(value = "itripUserRegisterVO", description = "用户注册")
public class ItripUserRegisterVO {

    @ApiModelProperty(value = "[必填]用户账号", required = true)
    private String userCode;

    @ApiModelProperty(value = "[必填]昵称", required = true)
    private String userName;

    @ApiModelProperty(value = "[必填]密码", required = true)
    private String userPassword;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
