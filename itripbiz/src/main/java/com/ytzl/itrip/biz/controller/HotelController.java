package com.ytzl.itrip.biz.controller;

import com.ytzl.itrip.beans.model.ItripAreaDic;
import com.ytzl.itrip.beans.model.ItripLabelDic;
import com.ytzl.itrip.beans.vo.ItripAreaDicVO;
import com.ytzl.itrip.beans.vo.ItripLabelDicVO;
import com.ytzl.itrip.biz.service.ItripAreaDicService;
import com.ytzl.itrip.biz.service.ItripLabelDicService;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sam on 2018/4/25.
 */
@Controller
@RequestMapping("/api/hotel")
@Api(description = "酒店业务模块")
public class HotelController {


    @Resource
    private ItripAreaDicService itripAreaDicService;

    @Resource
    private ItripLabelDicService itripLabelDicService;

    @ApiOperation(value = "查询（国内、国外）热门城市",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "查询国内、国外的热门城市(1:国内 2:国外) 成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：   \n" +
                    "错误码：  \n" +
                    "10202 : 系统异常,获取失败")
    @RequestMapping(value = "/queryhotcity/{type}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto queryhotcity(@PathVariable(value = "type")
                            @ApiParam(value = "类型", required = true)
                                    int type) {
        //isHot   1热门城市
        //isChina  1国内   2国外
        Map<String, Object> param = new HashMap();
        //热门城市
        param.put("isHot", "1");
        param.put("isChina", type);
        List<ItripAreaDicVO> itripAreaDicVOList = new ArrayList<>();
        try {
            List<ItripAreaDic> itripAreaDicList =
                    itripAreaDicService.getItripAreaDicListByMap(param);
            //遍历返回结果，将实体转换为VO类
            for (ItripAreaDic itripAreaDic : itripAreaDicList) {
                ItripAreaDicVO itripAreaDicVO = new ItripAreaDicVO();
                //自动复制 字段名称类型一致    第一个参数为有值  第二个参数是将要被赋值
                BeanUtils.copyProperties(itripAreaDic, itripAreaDicVO);
                itripAreaDicVOList.add(itripAreaDicVO);
            }
            //返回结果
            return DtoUtil.returnDataSuccess(itripAreaDicVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", ErrorCode.BIZ_HOTEL_QUERY_HOTCITY_FAIL);
        }
    }


    @ApiOperation(value = "查询酒店特色列表",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "获取酒店特色(用于查询页列表)  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ \n" +
                    "并返回错误码，如下：  错误码:  \n" +
                    "10205: 系统异常,获取失败")
    @RequestMapping(value = "/queryhotelfeature",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto queryhotelfeature() {
        Map<String, Object> param = new HashMap<>();
        param.put("parentId", "16");
        List<ItripLabelDicVO> itripLabelDicVOList = new ArrayList<>();
        try {
            //查询酒店特色列表
            List<ItripLabelDic> itripLabelDicList =
                    itripLabelDicService.getItripLabelDicListByMap(param);
            for (ItripLabelDic itripLabelDic : itripLabelDicList) {
                ItripLabelDicVO itripLabelDicVO = new ItripLabelDicVO();
                BeanUtils.copyProperties(itripLabelDic, itripLabelDicVO);
                itripLabelDicVOList.add(itripLabelDicVO);
            }
            return DtoUtil.returnDataSuccess(itripLabelDicVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败",
                    ErrorCode.BIZ_HOTEL_QUERY_HOTEL_FEATURE_FAIL);
        }
    }

    @ApiOperation(value = "根据城市查询商圈",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "根据城市查询商圈  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ \n" +
                    "并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "10204 : 系统异常,获取失败")
    @RequestMapping(value = "/querytradearea/{cityId}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto querytradearea(@PathVariable(value = "cityId")
                              @ApiParam(value = "城市Id", required = true)
                                      int cityId) {
        Map<String, Object> param = new HashMap<>();
        param.put("parent", cityId);
        //==1 商圈
        param.put("isTradingArea", "1");

        List<ItripAreaDicVO> itripAreaDicVOList = new ArrayList<>();
        try {
            List<ItripAreaDic> itripAreaDicList =
                    itripAreaDicService.getItripAreaDicListByMap(param);
            //遍历返回结果，将实体转换为VO类
            for (ItripAreaDic itripAreaDic : itripAreaDicList) {
                ItripAreaDicVO itripAreaDicVO = new ItripAreaDicVO();
                //自动复制 字段名称类型一致    第一个参数为有值  第二个参数是将要被赋值
                BeanUtils.copyProperties(itripAreaDic, itripAreaDicVO);
                itripAreaDicVOList.add(itripAreaDicVO);
            }
            //返回结果
            return DtoUtil.returnDataSuccess(itripAreaDicVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("程序异常，获取失败",
                    ErrorCode.BIZ_HOTEL_QUERY_TRADE_AREA_FAIL);
        }

    }

}
