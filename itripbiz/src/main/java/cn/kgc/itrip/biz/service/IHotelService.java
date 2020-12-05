package cn.kgc.itrip.biz.service;

import cn.kgc.itrip.beans.common.ServerResponse;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 21:16
 */
public interface IHotelService {
    public ServerResponse getHotCity(Integer type);

    public ServerResponse getHotelFeature();

    public ServerResponse gethotelfacilities(Integer id) throws Exception;
    public ServerResponse itripSearchDetailsHotelVO(Integer id) throws Exception;
    public ServerResponse querytradearea(Integer cityId) throws Exception;

    /**
     * 根据酒店id查询酒店政策
     * @param id
     * @return
     * @throws Exception
     */
    public ServerResponse queryhotelpolicy(Integer id)throws Exception;

    /**
     * 根据酒店id查询酒店特色、商圈、酒店名称
     * @param id
     * @return
     * @throws Exception
     */
    public ServerResponse getvideodesc(Integer id)throws Exception;

    /**
     * 根据targetId查询酒店图片(type=0)
     * @param targetId
     * @return
     * @throws Exception
     */
    public ServerResponse getImg(Integer targetId)throws Exception;

}
