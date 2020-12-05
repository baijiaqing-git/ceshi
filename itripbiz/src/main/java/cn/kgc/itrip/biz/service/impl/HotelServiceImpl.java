package cn.kgc.itrip.biz.service.impl;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripAreaDic;
import cn.kgc.itrip.beans.vo.ItripHotCityVo;
import cn.kgc.itrip.beans.vo.hotel.HotelVideoDescVO;
import cn.kgc.itrip.beans.vo.hotel.ItripSearchDetailsHotelVO;
import cn.kgc.itrip.biz.service.IHotelService;
import cn.kgc.itrip.dao.mapper.itripAreaDic.ItripAreaDicMapper;
import cn.kgc.itrip.dao.mapper.itripHotel.ItripHotelMapper;
import cn.kgc.itrip.dao.mapper.itripImage.ItripImageMapper;
import cn.kgc.itrip.dao.mapper.itripLabelDic.ItripLabelDicMapper;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 21:20
 */
@Service("iHotelService")
public class HotelServiceImpl implements IHotelService {
    @Resource
    private ItripAreaDicMapper itripAreaDicMapper;

    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    @Resource
    private ItripHotelMapper itripHotelMapper;

    @Resource
    private ItripImageMapper itripImageMapper;


    @Override
    public ServerResponse getHotCity(Integer type) {
        if (type == null) {
            return ServerResponse.createByErrorMessage("参数丢失");
        }
        List<ItripAreaDic> itripAreaDics = itripAreaDicMapper.queryHotCity(type);
        if (CollectionUtils.isEmpty(itripAreaDics)) {
            //检查是否查询数据
            return ServerResponse.createByErrorMessage("数据查询失败，请检查数据库服务是否打开");
        }
        List<ItripHotCityVo> hotCityVos = new LinkedList<>();
        for (ItripAreaDic i : itripAreaDics) {
            ItripHotCityVo itripHotCityVo = new ItripHotCityVo();
            BeanUtils.copyProperties(i, itripHotCityVo);
            hotCityVos.add(itripHotCityVo);
        }
        return ServerResponse.createBySuccess(hotCityVos);
    }


    @Override
    public ServerResponse getHotelFeature() {
        //todo 这里可以做一个判断
        return ServerResponse.createBySuccess(itripLabelDicMapper.getHotelFeature());
    }


    /**
     * 查询酒店设施
     */
    @Override
    public ServerResponse gethotelfacilities(Integer id) throws Exception {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数丢失...");
        }
        return ServerResponse.createBySuccess(itripHotelMapper.queryhotelfacilities(id));
    }

    /**
     * 酒店介绍及特色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse itripSearchDetailsHotelVO(Integer id) throws Exception {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数丢失...");
        }
        List<ItripSearchDetailsHotelVO> hotCityVos = new LinkedList<>();
        ItripSearchDetailsHotelVO itripSearchDetailsHotelVO = new ItripSearchDetailsHotelVO();
        itripSearchDetailsHotelVO.setName("酒店介绍");
        itripSearchDetailsHotelVO.setDescription(itripHotelMapper.itripSearchDetailsHotelVO(id));
        hotCityVos = itripHotelMapper.itripSearchDetailsHotelVOs(id);
        hotCityVos.add(itripSearchDetailsHotelVO);
        return ServerResponse.createBySuccess(hotCityVos);
    }

    /**
     * 查询商圈
     * @param cityId
     * @return
     */
    @Override
    public ServerResponse querytradearea(Integer cityId) throws Exception {
        if (cityId == null){
            return ServerResponse.createByErrorMessage("参数丢失...");
        }
        return ServerResponse.createBySuccess(itripAreaDicMapper.queryTrading(cityId)) ;
    }


    /**
     * 根据酒店id查询酒店政策
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse queryhotelpolicy(Integer id) throws Exception {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数丢失...");
        }
        return ServerResponse.createBySuccess(itripHotelMapper.queryhotelpolicy(id));
    }


    /**
     * 根据酒店id查询酒店特色、商圈、酒店名称
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse getvideodesc(Integer id) throws Exception {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数丢失...");
        }
        HotelVideoDescVO hotelVideoDescVO = new HotelVideoDescVO();
        hotelVideoDescVO.setHotelFeatureList(itripHotelMapper.getHotelFeatureList(id));
        hotelVideoDescVO.setHotelName(itripHotelMapper.getHotelName(id));
        hotelVideoDescVO.setTradingAreaNameList(itripHotelMapper.getTradingAreaNameList(id));
        return ServerResponse.createBySuccess(hotelVideoDescVO);
    }

    /**
     * 根据targetId查询酒店图片(type=0)
     * @param targetId
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse getImg(Integer targetId) throws Exception {
        if (targetId ==null){
            return ServerResponse.createByErrorMessage("参数丢失");
        }
        return ServerResponse.createBySuccess(itripImageMapper.getImg(targetId));
    }
}

