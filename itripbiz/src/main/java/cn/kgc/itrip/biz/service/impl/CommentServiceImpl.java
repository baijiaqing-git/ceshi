package cn.kgc.itrip.biz.service.impl;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripComment;
import cn.kgc.itrip.beans.model.ItripImage;
import cn.kgc.itrip.beans.model.ItripUser;
import cn.kgc.itrip.beans.vo.comment.ItripAddCommentVO;
import cn.kgc.itrip.beans.vo.comment.ItripCommentsVo;
import cn.kgc.itrip.beans.vo.comment.ItripListCommentVO;
import cn.kgc.itrip.beans.vo.comment.ItripSearchCommentVO;
import cn.kgc.itrip.biz.service.IHotelCommentService;
import cn.kgc.itrip.dao.mapper.itripComment.ItripCommentMapper;
import cn.kgc.itrip.dao.mapper.itripImage.ItripImageMapper;
import cn.kgc.itrip.dao.mapper.itripLabelDic.ItripLabelDicMapper;
import cn.kgc.itrip.utils.BigDecimalUtil;
import cn.kgc.itrip.utils.Page;
import cn.kgc.itrip.utils.RedisApi;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 14:29
 */
@Slf4j
@Service
public class CommentServiceImpl implements IHotelCommentService {
    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    @Resource
    private ItripImageMapper itripImageMapper;

    @Resource
    private ItripCommentMapper itripCommentMapper;
    @Resource
    private RedisApi redisApi;

    /**
     * 根据targetId查询评论照片（类型= 2）
     */
    public ServerResponse getimg(Integer targetId)throws Exception{
        return ServerResponse.createBySuccess(itripImageMapper.getCommentImg(targetId));
    }

    /**
     * 查询出游类型列表
     * @return
     * @throws Exception
     */
    public ServerResponse gettraveltype()throws Exception{
        return ServerResponse.createBySuccess(itripLabelDicMapper.gettraveltype());
    }

    /**
     * 根据酒店id查询酒店平均分
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ServerResponse gethotelscore(Long hotelId)throws Exception{
        return ServerResponse.createBySuccess(itripCommentMapper.getCommentAvgScore(hotelId));
    }

    /**
     * 根据酒店id查询类别评论数量
     * @param hotelId
     * @return
     * @throws Exception
     */
    public ServerResponse getcount(Integer hotelId)throws Exception{
        return ServerResponse.createBySuccess(itripCommentMapper.getcount(hotelId));
    }

    /**
     * 获取酒店相关信息(酒店名称，酒店星级)
     * @param hotelId
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse  gethoteldesc(Integer hotelId)throws Exception{
        return ServerResponse.createBySuccess(itripCommentMapper.gethoteldesc(hotelId));
    }

    /**
     * 图片删除接口
     * @param imgName
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse delpic(String imgName) throws Exception {
        Integer delpic=itripImageMapper.delpic(imgName);
        if(delpic==1){
            return ServerResponse.createBySuccessMessage("删除图片成功...");
        }
        return ServerResponse.createByErrorMessage("删除图片失败...");
    }

    /**
     * 新增评论接口
     * @param itripAddCommentVO
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse add(ItripAddCommentVO itripAddCommentVO, String token) throws Exception{
        //校验用户是否存在
        if(StringUtils.isBlank(token)){
            log.error("参数丢失");
            return ServerResponse.createByErrorMessage("用户令牌丢失");
        }
        if (!redisApi.exists(token)){
            log.error("用户令牌失效，需要重新登录");
            return ServerResponse.createByErrorMessage("用户需要重新登录");
        }
        Integer userId = JSONObject.parseObject(redisApi.get(token), ItripUser.class).getId().intValue();
        int ints = itripCommentMapper.add(itripAddCommentVO,userId);
        if(ints==1) {
            return ServerResponse.createBySuccessMessage("新增评论成功！");
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    /**
     * 根据评论类型查询评论列表，并分页显示
     * @param itripSearchCommentVO
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse getcommentlist(ItripSearchCommentVO itripSearchCommentVO)throws Exception{
        ItripCommentsVo itripCommentVos = new ItripCommentsVo();
        //获取总条数
        Integer count = itripCommentMapper.queryCommentCount(itripSearchCommentVO,itripSearchCommentVO.getHotelId().intValue());
        Page<Object> objectPage = new Page<>(itripSearchCommentVO.getPageNo(),itripSearchCommentVO.getPageSize(),count);

        List<ItripListCommentVO> itripListCommentVOS = itripCommentMapper.queryitripReturnCommentVo(
                itripSearchCommentVO,itripSearchCommentVO.getHotelId().intValue(),objectPage.getBeginPos(),objectPage.getPageSize());
        itripCommentVos.setBeginPos(objectPage.getBeginPos());
        itripCommentVos.setCurPage(objectPage.getCurPage());
        itripCommentVos.setPageCount(objectPage.getPageCount());
        itripCommentVos.setPageSize(objectPage.getPageSize());
        itripCommentVos.setRows(itripListCommentVOS);
        itripCommentVos.setTotal(count);
        return ServerResponse.createBySuccess(itripCommentVos);
    }

    @Override
    public boolean itriptxAddItripComment(ItripComment obj, List<ItripImage> itripImages) throws Exception {
        if (null != obj) {
            //计算综合评分，综合评分=(设施+卫生+位置+服务)/4
            float score = 0;
            int sum = obj.getFacilitiesScore() + obj.getHygieneScore() + obj.getPositionScore() + obj.getServiceScore();
            score = BigDecimalUtil.OperationASMD(sum, 4, BigDecimalUtil.BigDecimalOprations.divide, 1, BigDecimal.ROUND_DOWN).floatValue();
            //对结果四舍五入
            obj.setScore(Math.round(score));
            Long commentId = 0L;
            if (itripCommentMapper.insertItripComment(obj) > 0) {
                commentId = obj.getId();
                if (null != itripImages && itripImages.size() > 0 && commentId > 0) {
                    for (ItripImage itripImage : itripImages) {
                        itripImage.setTargetId(commentId);
                        itripImageMapper.insertItripImage(itripImage);
                    }
                }
                //更新订单表-订单状态为4（已评论）
                //itripHotelOrderMapper.updateHotelOrderStatus(obj.getOrderId(),obj.getCreatedBy());
                return true;
            }
        }
        return false;
    }
}
