package com.ytzl.itrip.biz.service.impl;

import com.ytzl.itrip.beans.model.ItripOrderLinkUser;
import com.ytzl.itrip.beans.model.ItripUserLinkUser;
import com.ytzl.itrip.beans.vo.ItripHotelOrderStoreVO;
import com.ytzl.itrip.biz.service.ItripHotelOrderService;
import com.ytzl.itrip.biz.service.ItripOrderLinkUserService;
import com.ytzl.itrip.dao.mapper.ItripHotelOrderMapper;
import com.ytzl.itrip.beans.model.ItripHotelOrder;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ytzl.itrip.utils.common.Constants;

@Service("itripHotelOrderService")
public class ItripHotelOrderServiceImpl implements ItripHotelOrderService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @Resource
    private ItripOrderLinkUserService itripOrderLinkUserService;

    public ItripHotelOrder getItripHotelOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderById(id);
    }

    public List<ItripHotelOrder> getItripHotelOrderListByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderListByMap(param);
    }

    public Integer getItripHotelOrderCountByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderCountByMap(param);
    }

    public Map<String,Object> saveItripHotelOrder(ItripHotelOrder itripHotelOrder,
                                       List<ItripUserLinkUser> linkUserList) throws Exception {
        int flag = 0;
        if (EmptyUtils.isNotEmpty(itripHotelOrder.getId())) {
            itripOrderLinkUserService.removeItripOrderLinkUserByOrderId(
                    itripHotelOrder.getId());
            itripHotelOrder.setModifyDate(new Date());
            flag = itripHotelOrderMapper.modifyItripHotelOrder(itripHotelOrder);
        } else {
            itripHotelOrder.setCreationDate(new Date());
            flag = itripHotelOrderMapper.saveItripHotelOrder(itripHotelOrder);
        }
        if (flag > 0) {
            for (ItripUserLinkUser itripUserLinkUser : linkUserList) {
                ItripOrderLinkUser itripOrderLinkUser = new ItripOrderLinkUser();
                itripOrderLinkUser.setCreationDate(new Date());
                itripOrderLinkUser.setLinkUserId(itripUserLinkUser.getId());
                itripOrderLinkUser.setLinkUserName(itripUserLinkUser.getLinkUserName());
                itripOrderLinkUser.setOrderId(itripHotelOrder.getId());
                itripOrderLinkUser.setCreatedBy(itripHotelOrder.getCreatedBy());
                //保存到数据库
                itripOrderLinkUserService.saveItripOrderLinkUser(itripOrderLinkUser);
            }
        }
        Map<String,Object> param = new HashMap();
        param.put("id",itripHotelOrder.getId());
        param.put("orderNo",itripHotelOrder.getOrderNo());
        return  param;
    }

    public Integer modifyItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception {
        itripHotelOrder.setModifyDate(new Date());
        return itripHotelOrderMapper.modifyItripHotelOrder(itripHotelOrder);
    }

    public Integer removeItripHotelOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.removeItripHotelOrderById(id);
    }

    public Page<ItripHotelOrder> queryItripHotelOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripHotelOrderMapper.getItripHotelOrderCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelOrder> itripHotelOrderList = itripHotelOrderMapper.getItripHotelOrderListByMap(param);
        page.setRows(itripHotelOrderList);
        return page;
    }

    @Override
    public List<ItripHotelOrderStoreVO> queryHotelStore(Map<String, Object> param) throws Exception {
        //调用库存检查
        itripHotelOrderMapper.flushStore(param);
        //调用库存计算
        return itripHotelOrderMapper.queryHotelStore(param);
    }

    @Override
    public Boolean validateHotelStore(Map<String, Object> param) {
        //获取预订数量
        int count = Integer.parseInt(param.get("count").toString());
        List<ItripHotelOrderStoreVO> storeVOList =
                itripHotelOrderMapper.queryHotelStore(param);
        if (null == storeVOList || storeVOList.size() <= 0)
            return false;
        //  5   10
        if (storeVOList.get(0).getStore() < count)
            return false;
        return true;
    }

}
