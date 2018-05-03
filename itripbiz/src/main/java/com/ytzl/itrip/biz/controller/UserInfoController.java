package com.ytzl.itrip.biz.controller;

import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.beans.model.ItripUserLinkUser;
import com.ytzl.itrip.beans.vo.ItripSearchUserLinkUserVO;
import com.ytzl.itrip.beans.vo.ValidateRoomStoreVO;
import com.ytzl.itrip.biz.service.ItripUserLinkUserService;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.ValidationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2018/5/3.
 */
@Controller
@RequestMapping("/api/userinfo")
@Api(description = "常用联系人模块")
public class UserInfoController {

    @Resource
    private ItripUserLinkUserService itripUserLinkUserService;

    @Resource
    private ValidationUtil validationUtil;

    @ApiOperation(value = "查询常用联系人接口", httpMethod = "POST",
            produces = "application/json"
            , response = Dto.class,
            notes = "查询常用联系人信息(可根据联系人姓名进行模糊查询)  \n" +
                    "若不根据联系人姓名进行查询，不输入参数即可 | 若根据联系人姓名进行查询，须进行相应的入参，比如：{\"linkUserName\":\"张三\"}  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100401 : 获取常用联系人信息失败  \n" +
                    "100000 : token失效，请重登录")
    @RequestMapping(value = "/queryuserlinkuser",
            method = RequestMethod.POST,
            produces = "application/json", headers = "token")
    //指定参数说明
    @ApiImplicitParam(required = true, value = "唯一凭证",
            name = "token", paramType = "header")
    @ResponseBody
    public Dto getpreorderinfo(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO, HttpServletRequest request) {
        ItripUser itripUser = validationUtil.getUser(request.getHeader("token"));
        if (null == itripUser) {
            return DtoUtil.returnFail("Token已失效", "100000");
        }
        Map<String, Object> param = new HashMap();
        param.put("userId", itripUser.getId());
        param.put("linkUserName", itripSearchUserLinkUserVO.getLinkUserName());
        try {
            List<ItripUserLinkUser> userList = itripUserLinkUserService.getItripUserLinkUserListByMap(param);
            return DtoUtil.returnSuccess("获取常用联系人信息成功", userList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取常用联系人信息失败", "100401");
        }
    }

}
