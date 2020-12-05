package cn.kgc.itrip.dao.mapper.itripImage;

import cn.kgc.itrip.beans.model.ItripImage;
import cn.kgc.itrip.beans.vo.ItripImgVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripImageMapper {

    /**
     * 根据targetId查询酒店图片(type=0)
     * @param targetId
     * @return
     * @throws Exception
     */
    public List<ItripImgVo> getImg(int targetId)throws Exception;

    /**
     * 根据targetId查询酒店房型图片（type = 1）
     */
    public List<ItripImgVo> getRoomImg(int targetId)throws Exception;

    /**
     * 根据targetId查询酒店房型图片（type = 2）
     */
    public List<ItripImgVo>getCommentImg(int targetId)throws Exception;

    /**
     * 删除图片上传
     * @param imgName
     * @return
     * @throws Exception
     */
    public Integer delpic(String imgName)throws Exception;

    public Integer insertItripImage(ItripImage itripImage)throws Exception;
}
