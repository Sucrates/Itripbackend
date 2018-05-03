package com.ytzl.itrip.biz.controller;

import com.ytzl.itrip.beans.model.ItripComment;
import com.ytzl.itrip.beans.model.ItripImage;
import com.ytzl.itrip.beans.vo.ItripCommentCountVO;
import com.ytzl.itrip.beans.vo.ItripCommentScoreVO;
import com.ytzl.itrip.beans.vo.ItripImageVO;
import com.ytzl.itrip.beans.vo.ItripSearchCommentVO;
import com.ytzl.itrip.biz.service.ItripCommentService;
import com.ytzl.itrip.biz.service.ItripImageService;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2018/5/2.
 */
@Controller
@RequestMapping("/api/comment")
@Api(description = "酒店评论模块")
public class ItripCommentController {

    @Resource
    private ItripCommentService itripCommentService;

    @Resource
    private ItripImageService itripImageService;

    @ApiOperation(value = "据酒店id查询酒店平均分",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "总体评分(score)、位置评分(positionScore)、设施评分(facilitiesScore)、服务评分(serviceScore)、卫生评分(hygieneScore)  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100001 : 获取评分失败  \n" +
                    "100002 : hotelId不能为空")
    @RequestMapping(value = "/gethotelscore/{hotelId}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto gethotelscore(@PathVariable(value = "hotelId")
                             @ApiParam(value = "酒店Id", required = true)
                                     Long hotelId) {
        //查询酒店平均分
        try {
            ItripCommentScoreVO itripCommentScoreVO =
                    itripCommentService.getHotelScoreByHotelId(hotelId);
            return DtoUtil.returnSuccess("获取评分成功", itripCommentScoreVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取评分失败", "100001");
        }

    }


    @ApiOperation(value = "根据酒店id查询各类评论数量",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "根据酒店id查询评论数量（全部评论、值得推荐、有待改善、有图片）  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100014 : 获取酒店总评论数失败  \n" +
                    "100015 : 获取酒店有图片评论数失败  \n" +
                    "100016 : 获取酒店有待改善评论数失败  \n" +
                    "100017 : 获取酒店值得推荐评论数失败  \n" +
                    "100018 : 参数hotelId为空")
    @RequestMapping(value = "/getcount/{hotelId}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto getcount(@PathVariable(value = "hotelId")
                        @ApiParam(value = "酒店Id", required = true)
                                Long hotelId) {
        // hotelId   isOk  isHavingImg
        Map<String, Object> param = new HashMap();
        //查询所有评论总数
        param.put("hotelId", hotelId);
        //总评论数
        Integer allcomment;
        try {
            //总评论数
            allcomment = itripCommentService.getItripCommentCountByMap(param);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店总评论数失败", "100014");
        }
        //查询有待改善评论数
        param.put("isOk", 0);
        //有待改善评论数
        Integer improve;
        try {
            //有待改善评论数
            improve = itripCommentService.getItripCommentCountByMap(param);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店有待改善评论数失败", "100016");
        }
        //查询值得推荐评论数
        param.put("isOk", 1);
        //值得推荐评论数
        Integer isok;
        try {
            //值得推荐评论数
            isok = itripCommentService.getItripCommentCountByMap(param);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店值得推荐评论数失败", "100017");
        }
        //查询有图片评论数
        param.put("isOk", null);
        param.put("isHavingImg", 1);
        //有图片评论数
        Integer havingimg;
        try {
            //有图片评论数
            havingimg = itripCommentService.getItripCommentCountByMap(param);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店有图片评论数失败", "100015");
        }
        //返回结果
        return DtoUtil.returnSuccess("获取酒店各类评论数成功",
                new ItripCommentCountVO(improve, isok, havingimg, allcomment));

    }

    @ApiOperation(value = "根据评论类型查询评论列表，并分页显示",
            httpMethod = "POST", response = Dto.class,
            produces = "application/json",
            notes = "根据评论类型查询评论列表，并分页显示  \n" +
                    "参数数据e.g：  \n" +
                    "全部评论：{\"hotelId\":10,\"isHavingImg\":-1,\"isOk\":-1,\"pageSize\":5,\"pageNo\":1}  \n" +
                    "有图片：{\"hotelId\":10,\"isHavingImg\":1,\"isOk\":-1,\"pageSize\":5,\"pageNo\":1}  \n" +
                    "值得推荐：{\"hotelId\":10,\"isHavingImg\":-1,\"isOk\":1,\"pageSize\":5,\"pageNo\":1}  \n" +
                    "有待改善：{\"hotelId\":10,\"isHavingImg\":-1,\"isOk\":0,\"pageSize\":5,\"pageNo\":1}  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100020 : 获取评论列表错误")
    @RequestMapping(value = "/getcommentlist",
            method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public Dto getcommentlist(@RequestBody ItripSearchCommentVO itripSearchCommentVO) {
        if (itripSearchCommentVO.getIsOk() == -1) {
            itripSearchCommentVO.setIsOk(null);
        }
        if (itripSearchCommentVO.getIsHavingImg() == -1) {
            itripSearchCommentVO.setIsHavingImg(null);
        }
        //酒店Id不能为空
        if (EmptyUtils.isEmpty(itripSearchCommentVO.getHotelId())) {
            return DtoUtil.returnFail("酒店Id不能为空", "100020");
        }
        //构建查询查数
        Map<String, Object> param = new HashMap<>();
        param.put("hotelId", itripSearchCommentVO.getHotelId());
        param.put("isOk", itripSearchCommentVO.getIsOk());
        param.put("isHavingImg", itripSearchCommentVO.getIsHavingImg());
        //查询返回结果
        try {
            Page<ItripComment> itripCommentPage = itripCommentService.queryItripCommentPageByMap(param,
                    itripSearchCommentVO.getPageNo(),
                    itripSearchCommentVO.getPageSize());
            return DtoUtil.returnDataSuccess(itripCommentPage);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取评论列表错误", "100020");
        }

    }




    @ApiOperation(value = "根据targetId查询酒店评论照片(type=2)",
            httpMethod = "GET", response = Dto.class,
            produces = "application/json",
            notes = "根据targetId查询酒店评论照片(type=2)  \n" +
                    "成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：  \n" +
                    "错误码：  \n" +
                    "100012 : 获取评论图片失败  \n" +
                    "100013 : 评论id不能为空")
    @RequestMapping(value = "/getimg/{targetId}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable(value = "targetId")
                      @ApiParam(value = "评论Id", required = true)
                              Long targetId) {
        //构建参数
        Map<String, Object> param = new HashMap<>();
        param.put("type", 2);
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
            return DtoUtil.returnSuccess("获取酒店评论照片成功",
                    itripImageVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店评论照片失败", "100012");
        }

    }



}
