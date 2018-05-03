package com.ytzl.itrip.auth.controller;

import com.ytzl.itrip.auth.service.ItripUserService;
import com.ytzl.itrip.auth.service.TokenService;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.beans.vo.ItripUserTokenVO;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.ErrorCode;
import com.ytzl.itrip.utils.exception.ItripException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javafx.geometry.Pos;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by sam on 2018/4/23.
 */
@Controller
@RequestMapping("/api")
@Api(description = "登录模块控制器")
public class LoginController {

    @Resource
    private ItripUserService itripUserService;

    @Resource
    private TokenService tokenService;

    @ApiOperation(value = "登录", httpMethod = "POST", produces = "application/json"
            , response = Dto.class, notes = "根据用户名、密码进行统一认证")
    @RequestMapping(value = "/dologin", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public Dto doLogin(@RequestParam("name")
                       @ApiParam(value = "用户账号", required = true) String name,
                       @RequestParam("password")
                       @ApiParam(value = "密码", required = true) String password,
                       HttpServletRequest request) {
        try {
            //调用service login方法登录并获取用户信息
            ItripUser itripUser = itripUserService.login(name, password);
            //构建Token
            String userAgent = request.getHeader("user-agent");
            String token = tokenService.generateToken(userAgent, itripUser);
            //保存Token
            tokenService.saveToken(token, itripUser);
            //返回客户端Token
            //获取当前时间
            long genTime = Calendar.getInstance().getTimeInMillis();
            ItripUserTokenVO itripUserTokenVO = new ItripUserTokenVO(token,//
                    genTime + 60 * 60 * 2 * 1000, genTime);
            return DtoUtil.returnDataSuccess(itripUserTokenVO);
        } catch (ItripException e) {
            e.printStackTrace();
            //返回错误信息
            return DtoUtil.returnFail(e.getMessage(), e.getErrorCode());
        }


    }

    @ApiOperation(value = "退出", httpMethod = "GET",
            produces = "application/json"
            , response = Dto.class,
            notes = "客户端需在header中发送token")
    @RequestMapping(value = "/logout",
            method = RequestMethod.GET,
            produces = "application/json", headers = "token")
    //指定参数说明
    @ApiImplicitParam(required = true, value = "唯一凭证",
            name = "token", paramType = "header")
    @ResponseBody
    public Dto logout(HttpServletRequest request) {
        //判断token是否有效

        String userAgent = request.getHeader("user-agent");
        String token = request.getHeader("token");
        if (tokenService.validateToken(token, userAgent)) {
            //有效则删除token并返回退出成功
            tokenService.removeToken(token);
            return DtoUtil.returnSuccess("注销成功");
        } else {
            //无效则返回token无效
            return DtoUtil.returnFail("Token无效", ErrorCode.AUTH_TOKEN_INVALID);
        }
    }

}
