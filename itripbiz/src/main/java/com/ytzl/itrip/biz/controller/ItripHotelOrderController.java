package com.ytzl.itrip.biz.controller;

import com.alibaba.fastjson.JSON;
import com.ytzl.itrip.beans.model.ItripHotelOrder;
import com.ytzl.itrip.beans.model.ItripHotelRoom;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.beans.model.ItripUserLinkUser;
import com.ytzl.itrip.beans.vo.ItripAddHotelOrderVO;
import com.ytzl.itrip.beans.vo.ItripHotelOrderStoreVO;
import com.ytzl.itrip.beans.vo.ValidateRoomStoreVO;
import com.ytzl.itrip.biz.service.ItripHotelOrderService;
import com.ytzl.itrip.biz.service.ItripHotelRoomService;
import com.ytzl.itrip.biz.service.ItripHotelService;
import com.ytzl.itrip.utils.common.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_DOWN;

/**
 * Created by sam on 2018/5/3.
 */
@Controller
@RequestMapping("/api/hotelorder")
@Api(description = "酒店订单模块")
public class ItripHotelOrderController {

    @Resource
    private ItripHotelOrderService itripHotelOrderService;

    @Resource
    private ItripHotelService itripHotelService;

    @Resource
    private ItripHotelRoomService itripHotelRoomService;

    @Resource
    private ValidationUtil validationUtil;

    @ApiOperation(value = "生成订单前,获取预订信息", httpMethod = "POST",
            produces = "application/json"
            , response = Dto.class,
            notes = "生成订单前,获取预订信息  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100000 : token失效，请重登录  \n" +
                    "100510 : hotelId不能为空  \n" +
                    "100511 : roomId不能为空  \n" +
                    "100513 : 系统异常")
    @RequestMapping(value = "/getpreorderinfo",
            method = RequestMethod.POST,
            produces = "application/json", headers = "token")
    //指定参数说明
    @ApiImplicitParam(required = true, value = "唯一凭证",
            name = "token", paramType = "header")
    @ResponseBody
    public Dto getpreorderinfo(@RequestBody ValidateRoomStoreVO validateRoomStoreVO,
                               HttpServletRequest request) {
        //token是否失效
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (null == itripUser) {
            return DtoUtil.returnFail("Token已失效，请重新登录", "100000");
        }
        //酒店Id不能为空
        if (EmptyUtils.isEmpty(validateRoomStoreVO.getHotelId())) {
            return DtoUtil.returnFail("酒店Id不能为空", "100510");
        }
        //房型Id不能为空
        if (EmptyUtils.isEmpty(validateRoomStoreVO.getRoomId())) {
            return DtoUtil.returnFail("房型Id不能为空", "100511");
        }
        //构建查询参数
        Map<String, Object> param = new HashMap();
        param.put("startTime", validateRoomStoreVO.getCheckInDate());
        param.put("endTime", validateRoomStoreVO.getCheckOutDate());
        param.put("roomId", validateRoomStoreVO.getRoomId());
        param.put("hotelId", validateRoomStoreVO.getHotelId());
        //获取酒店房间库存，最小库存
        try {
            List<ItripHotelOrderStoreVO> storeVOList =
                    itripHotelOrderService.queryHotelStore(param);
            if (EmptyUtils.isEmpty(storeVOList) || storeVOList.size() <= 0) {
                return DtoUtil.returnFail("入住时间必须大于退房时间",
                        "100513");
            }
            //获取库存
            int store = storeVOList.get(0).getStore();
            //创建返回结果
            ItripHotelOrderStoreVO hotelOrderStoreVO = new ItripHotelOrderStoreVO();
            BeanUtils.copyProperties(validateRoomStoreVO, hotelOrderStoreVO);
            hotelOrderStoreVO.setStore(store);
            //查询酒店名称
            String hotelName = itripHotelService.getItripHotelById(
                    validateRoomStoreVO.getHotelId()).getHotelName();
            hotelOrderStoreVO.setHotelName(hotelName);
            //查询房型价格
            BigDecimal roomPrice = itripHotelRoomService.getItripHotelRoomById(
                    validateRoomStoreVO.getRoomId()).getRoomPrice();
            hotelOrderStoreVO.setPrice(roomPrice.doubleValue());
            return DtoUtil.returnSuccess("获取成功", hotelOrderStoreVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "100513");
        }
    }


    @ApiOperation(value = "生成订单", httpMethod = "POST",
            produces = "application/json"
            , response = Dto.class,
            notes = "生成订单  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100505 : 生成订单失败  \n" +
                    "100506 : 不能提交空，请填写订单信息  \n" +
                    "100507 : 库存不足  \n" +
                    "100000 : token失效，请重登录")
    @RequestMapping(value = "/addhotelorder",
            method = RequestMethod.POST,
            produces = "application/json", headers = "token")
    //指定参数说明
    @ApiImplicitParam(required = true, value = "唯一凭证",
            name = "token", paramType = "header")
    @ResponseBody
    public Dto addhotelorder(@RequestBody ItripAddHotelOrderVO itripAddHotelOrderVO,
                             HttpServletRequest request) {
        String token = request.getHeader("token");
        //获取用户
        ItripUser itripUser = validationUtil.getUser(token);
        if (null == itripUser) {
            return DtoUtil.returnFail("Token已失效", "100000");
        }
        //不能提交空，请填写订单信息
        if (null == itripAddHotelOrderVO) {
            return DtoUtil.returnFail("不能提交空，请填写订单信息",
                    "100506");
        }
        //退房日期必须大于入住日期
        //28 30   28 29
        int dates = DateUtil.getBetweenDates(itripAddHotelOrderVO.getCheckInDate(),
                itripAddHotelOrderVO.getCheckOutDate()).size();
        if (dates <= 0) {
            return DtoUtil.returnFail("退房日期必须大于入住日期",
                    "100505");
        }
        //构建库存查询参数
        Map<String, Object> param = new HashMap();
        param.put("startTime", itripAddHotelOrderVO.getCheckInDate());
        param.put("endTime", itripAddHotelOrderVO.getCheckOutDate());
        param.put("roomId", itripAddHotelOrderVO.getRoomId());
        param.put("hotelId", itripAddHotelOrderVO.getHotelId());
        param.put("count", itripAddHotelOrderVO.getCount());
        // 库存是否充足
        Boolean isok = itripHotelOrderService.validateHotelStore(param);
        if (isok) {
            try {
                ItripHotelOrder itripHotelOrder = new ItripHotelOrder();
                BeanUtils.copyProperties(itripAddHotelOrderVO, itripHotelOrder);
                //天数
                itripHotelOrder.setBookingDays(dates);
                //用户Id
                itripHotelOrder.setUserId(itripUser.getId());
                //默认待支付状态
                itripHotelOrder.setOrderStatus(0);
                //创建人
                itripHotelOrder.setCreatedBy(itripUser.getId());
                //下单平台
                if (token.startsWith("PC-")) {
                    itripHotelOrder.setBookType(0);
                } else if (token.startsWith("MOBILE-")) {
                    itripHotelOrder.setBookType(1);
                } else {
                    itripHotelOrder.setBookType(2);
                }
                //获取相关联的用户
                List<ItripUserLinkUser> linkUserList = itripAddHotelOrderVO.getLinkUser();
                String linkUserName = "";
                for (ItripUserLinkUser linkUser : linkUserList) {
                    linkUserName += linkUser.getLinkUserName() + ",";
                }
                //去掉最后一个逗号
                itripHotelOrder.setLinkUserName(
                        linkUserName.substring(0, linkUserName.length() - 1));
                //订单金额计算  房间数量*房间价格
                int roomCount = itripAddHotelOrderVO.getCount() * dates;
                //根据RoomId查询房间价格

                ItripHotelRoom itripHotelRoom = itripHotelRoomService.getItripHotelRoomById(
                        itripAddHotelOrderVO.getRoomId());
                //55.55
                BigDecimal price = BigDecimalUtil.OperationASMD(roomCount, itripHotelRoom.getRoomPrice(),
                        BigDecimalUtil.BigDecimalOprations.multiply, 2,
                        BigDecimal.ROUND_DOWN);
                itripHotelOrder.setPayAmount(price);
                //生成订单号：机器码 +日期+（MD5）（商品IDs+毫秒数+1000000的随机数)
                StringBuffer orderNo = new StringBuffer();
                orderNo.append(PropertiesUtils.get("public.properties",
                        "systemMachineCode"));
                orderNo.append(new SimpleDateFormat("yyyyMMddHHmmss").
                        format(new Date()));
                String preMd5Str = itripAddHotelOrderVO.getRoomId() +
                        System.currentTimeMillis() +
                        new Random().nextInt(10000000) + "";
                orderNo.append(DigestUtil.hmacSign(preMd5Str));
                itripHotelOrder.setOrderNo(orderNo.toString());
                Map<String, Object> resultMap = itripHotelOrderService.
                        saveItripHotelOrder(itripHotelOrder, linkUserList);
                return DtoUtil.returnSuccess("生成订单成功",resultMap);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("生成订单失败，系统异常",
                        "100505");
            }

        } else {
            return DtoUtil.returnFail("库存不足",
                    "100507");
        }
    }

}
