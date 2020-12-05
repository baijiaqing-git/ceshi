package cn.kgc.itrip.biz.service.impl;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripLabelDic;
import cn.kgc.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.kgc.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import cn.kgc.itrip.beans.vo.mappervo.HotelRoomMapperVo;
import cn.kgc.itrip.biz.service.ItripHotelRoomService;
import cn.kgc.itrip.dao.mapper.itripHotelRoom.ItripHotelRoomMapper;
import cn.kgc.itrip.dao.mapper.itripImage.ItripImageMapper;
import cn.kgc.itrip.dao.mapper.itripLabelDic.ItripLabelDicMapper;
import cn.kgc.itrip.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 12:13
 */
@Service
public class HotelRoomServiceImpl implements ItripHotelRoomService {
    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;

    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    @Resource
    private ItripImageMapper itripImageMapper;

    /**
     * 查询酒店房型列表
     *
     * @param searchHotelRoomVO
     * @return
     */
    @Override
    public ServerResponse queryhotelroombyhotel(SearchHotelRoomVO searchHotelRoomVO) {
        HotelRoomMapperVo hotelRoomMapperVo = new HotelRoomMapperVo();
        BeanUtils.copyProperties(searchHotelRoomVO, hotelRoomMapperVo);
        //获取入住时间和结束时间之间的每一天
        hotelRoomMapperVo.setOriginalRoomList(DateUtil.getBetweenDates(searchHotelRoomVO.getStartDate(), searchHotelRoomVO.getEndDate()));
        List<ItripHotelRoomVO> queryroombed = itripHotelRoomMapper.queryroombed(hotelRoomMapperVo);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(queryroombed)) {
            return ServerResponse.createByErrorMessage("查询酒店房型失败");
        }
        List<List<ItripHotelRoomVO>> listLists = new ArrayList<>();
        for (int i = 0; i < queryroombed.size(); i++) {
            List<ItripHotelRoomVO> itripHotelRoomVOS = new ArrayList<>();
            itripHotelRoomVOS.add(queryroombed.get(i));
            listLists.add(itripHotelRoomVOS);
        }
        return ServerResponse.createBySuccess(listLists);
    }

    /**
     * 根据targetId查询酒店图片（type=1）
     * @param targetId
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse getImg(Integer targetId) throws Exception {
        if (targetId ==null){
            return ServerResponse.createByErrorMessage("参数丢失");
        }
        return ServerResponse.createBySuccess(itripImageMapper.getRoomImg(targetId));
    }

    @Override
    public ServerResponse queryhotelroombed() throws Exception {
        return ServerResponse.createBySuccess(itripLabelDicMapper.queryhotelroombed());
    }
}
