package cn.kgc.itrip.dao.mapper.itripComment;

import cn.kgc.itrip.beans.model.ItripComment;
import cn.kgc.itrip.beans.vo.comment.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripCommentMapper {

    /**
     * 根据酒店id查询酒店平均分
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ItripScoreCommentVO getCommentAvgScore(@Param(value = "hotelId") Long hotelId) throws Exception;

    /**
     * 根据酒店id查询类别评论数量
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ItripCountVo getcount(Integer hotelId)throws Exception;

    /**
     * 获取酒店相关信息(酒店名称，酒店星级)
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ItripHotelDescVO  gethoteldesc(Integer hotelId)throws Exception;

    public Integer queryCommentCount(@Param("itripSearchCommentVO")ItripSearchCommentVO itripSearchCommentVO,
                                     @Param("hotelId")Integer hotelId)throws Exception;

    /**
     * 新增评论接口
     */
    public Integer add(@Param("itripAddCommentVO") ItripAddCommentVO itripAddCommentVO, @Param("userId") Integer userId)throws Exception;

    /**
     * 根据评论类型查询评论列表，并分页显示
     * @param itripSearchCommentVO
     * @param hotelId
     * @param beginPos
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<ItripListCommentVO> queryitripReturnCommentVo(@Param("itripSearchCommentVO")ItripSearchCommentVO itripSearchCommentVO,
                                                              @Param("hotelId")Integer hotelId,
                                                              @Param("beginPos")Integer beginPos,
                                                              @Param("pageSize")Integer pageSize)throws Exception;

    public Integer insertItripComment(ItripComment itripComment) throws Exception;

}
