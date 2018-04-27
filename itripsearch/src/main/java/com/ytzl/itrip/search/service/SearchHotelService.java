package com.ytzl.itrip.search.service;

import com.ytzl.itrip.beans.vo.SearchHotCityVO;
import com.ytzl.itrip.beans.vo.SearchHotelVO;
import com.ytzl.itrip.search.beans.Hotel;
import com.ytzl.itrip.utils.common.Page;

import java.util.List;

/**
 * Created by Su_crates on 2018/4/27.
 */
public interface SearchHotelService {

    public Page<Hotel> searchHotelPage(SearchHotelVO searchHotelVO) throws Exception;

    public List<Hotel> searchHotelByHotCity(SearchHotCityVO searchHotCityVO) throws Exception;
}
