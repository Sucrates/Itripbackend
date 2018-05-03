package com.ytzl.itrip.biz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ytzl.itrip.beans.model.ItripHotelRoom;
import com.ytzl.itrip.beans.model.ItripImage;
import com.ytzl.itrip.beans.model.ItripLabelDic;
import com.ytzl.itrip.beans.vo.ItripImageVO;
import com.ytzl.itrip.beans.vo.ItripLabelDicVO;
import com.ytzl.itrip.beans.vo.SearchHotelRoomVO;
import com.ytzl.itrip.biz.service.ItripHotelRoomService;
import com.ytzl.itrip.biz.service.ItripImageService;
import com.ytzl.itrip.biz.service.ItripLabelDicService;
import com.ytzl.itrip.utils.common.DateUtil;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.EmptyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sam on 2018/4/28.
 */
@Controller
@RequestMapping("/api/hotelroom")
@Api(description = "酒店房型")
public class ItripHotelRoomController {

    @Resource
    private ItripHotelRoomService itripHotelRoomService;

    @Resource
    private ItripLabelDicService itripLabelDicService;

    @Resource
    private ItripImageService itripImageService;

    @ApiOperation(value = "查询酒店房间列表",
            httpMethod = "POST", response = Dto.class,
            produces = "application/json",
            notes = "查询酒店房间列表  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100303 : 酒店id不能为空,酒店入住及退房时间不能为空,入住时间不能大于退房时间  \n" +
                    "100304 : 系统异常")
    @RequestMapping(value = "/queryhotelroombyhotel",
            method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public Dto queryhotelroombyhotel(
            @RequestBody SearchHotelRoomVO searchHotelRoomVO) {
        if (EmptyUtils.isEmpty(searchHotelRoomVO.getHotelId())) {
            return DtoUtil.returnFail("酒店Id不能为空", "100303");
        }
        if (EmptyUtils.isEmpty(searchHotelRoomVO.getStartDate())
                || EmptyUtils.isEmpty(searchHotelRoomVO.getEndDate())) {
            return DtoUtil.returnFail("酒店入住及退房时间不能为空", "100303");
        }
        List<Date> dateList = new ArrayList<>();
        try {
            Date startTime = DateUtil.parse(searchHotelRoomVO.getStartDate(),
                    "yyyy-MM-dd");
            Date endTime = DateUtil.parse(searchHotelRoomVO.getEndDate(),
                    "yyyy-MM-dd");
            if (startTime.getTime() > endTime.getTime()) {
                return DtoUtil.returnFail("入住时间不能大于退房时间",
                        "100303");
            }
            //28 - 30  28  29
            dateList = DateUtil.getBetweenDates(startTime, endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //将对象转换为map
        //new TypeReference<Map<String, Object>>() {}
        Map<String, Object> param = JSON.parseObject(
                JSON.toJSONString(searchHotelRoomVO), Map.class);
        param.put("dateList", dateList);
        try {
            List<ItripHotelRoom> itripHotelRoomList =
                    itripHotelRoomService.getItripHotelRoomListByMap(param);
            //返回结果
            List<List<ItripHotelRoom>> resultList = new ArrayList<>();
            for (ItripHotelRoom itripHotelRoom : itripHotelRoomList) {
                List<ItripHotelRoom> list = new ArrayList<>();
                list.add(itripHotelRoom);
                resultList.add(list);
            }
            return DtoUtil.returnSuccess("获取成功", resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取失败，系统异常", "100304");
        }
    }


    @ApiOperation(value = "查询酒店房间床型列表",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "查询酒店床型列表  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100305 : 获取酒店房间床型失败")
    @RequestMapping(value = "/queryhotelroombed",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto queryhotelroombed() {
//返回结果
        List<ItripLabelDicVO> itripLabelDicVOList = new ArrayList<>();
        Map<String, Object> param = new HashMap();
        param.put("parentId", 1);
        try {
            List<ItripLabelDic> itripLabelDicList = itripLabelDicService.getItripLabelDicListByMap(param);

            for (ItripLabelDic itripLabelDic : itripLabelDicList) {
                ItripLabelDicVO itripLabelDicVO = new ItripLabelDicVO();
                BeanUtils.copyProperties(itripLabelDic, itripLabelDicVO);
                itripLabelDicVOList.add(itripLabelDicVO);
            }
            return DtoUtil.returnSuccess("获取成功", itripLabelDicVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店房间床型失败", "100305");
        }
    }


    @ApiOperation(value = "根据targetId查询酒店房型图片(type=1)",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "根据酒店房型ID查询酒店房型图片  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100301 : 获取酒店房型图片失败  \n" +
                    "100302 : 酒店房型id不能为空")
    @RequestMapping(value = "/getimg/{targetId}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable(value = "targetId")
                      @ApiParam(value = "酒店房型Id", required = true)
                              Long targetId) {
        //构建参数
        Map<String, Object> param = new HashMap<>();
        param.put("type", 1);
        param.put("targetId", targetId);
        //查询
        try {
            List<ItripImage> itripImageList =
                    itripImageService.getItripImageListByMap(param);
            //返回结果
            List<ItripImageVO> itripImageVOList = new ArrayList<>();
            for (ItripImage itripImage : itripImageList) {
                itripImageVOList.add(new ItripImageVO(
                        itripImage.getPosition(),itripImage.getImgUrl()));
            }
            return DtoUtil.returnSuccess("获取酒店图片房型成功",
                    itripImageVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店房型图片失败", "100301");
        }

    }


}
