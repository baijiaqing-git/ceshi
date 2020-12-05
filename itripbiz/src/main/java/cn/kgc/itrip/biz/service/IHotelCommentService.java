package cn.kgc.itrip.biz.service;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripComment;
import cn.kgc.itrip.beans.model.ItripImage;
import cn.kgc.itrip.beans.vo.comment.ItripAddCommentVO;
import cn.kgc.itrip.beans.vo.comment.ItripListCommentVO;
import cn.kgc.itrip.beans.vo.comment.ItripSearchCommentVO;
import cn.kgc.itrip.utils.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 14:29
 */
public interface IHotelCommentService {

    /**
     * 查询出游列表
     * @return
     * @throws Exception
     */
    public ServerResponse gettraveltype() throws Exception;

    /**
     * 根据targetId查询评论照片（type=2）
     * @param targetId
     * @return
     * @throws Exception
     */
    public ServerResponse getimg(Integer targetId) throws Exception;

    /**
     * 根据酒店id查询酒店平均分
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ServerResponse gethotelscore(Long hotelId)throws Exception;

    /**
     * 根据酒店id查询各类评论数量
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ServerResponse getcount(Integer hotelId)throws Exception;

    /**
     * 获取酒店相关信息(酒店名称，酒店星级)
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ServerResponse  gethoteldesc(Integer hotelId)throws Exception;

    /**
     * 图片删除接口
     * @param imgName
     * @return
     * @throws Exception
     */
    public ServerResponse delpic(String imgName) throws Exception;

    /**
     * 新增评论接口
     * @param itripAddCommentVO
     * @param token
     * @return
     * @throws Exception
     */
    public ServerResponse add(ItripAddCommentVO itripAddCommentVO, String token) throws Exception;

    /**
     * 根据评论类型查询评论列表，并分页显示
     * @param itripSearchCommentVO
     * @return
     * @throws Exception
     */
    public ServerResponse getcommentlist(ItripSearchCommentVO itripSearchCommentVO)throws Exception;


    /**
     * 添加点评-add by hanlu
     * @param obj
     * @param itripImages
     * @return
     * @throws Exception
     */
    public boolean itriptxAddItripComment(ItripComment obj, List<ItripImage> itripImages)throws Exception;
}
