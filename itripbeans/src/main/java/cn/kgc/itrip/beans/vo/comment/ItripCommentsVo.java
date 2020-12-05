package cn.kgc.itrip.beans.vo.comment;

import lombok.Data;

import java.util.List;

/**
 * @author 杨幸运
 * @create 2020/10/1
 */
@Data
public class ItripCommentsVo {
    private Integer beginPos;
    private Integer curPage;
    private Integer pageCount;
    private Integer pageSize;
    private List<ItripListCommentVO> rows;
    private Integer total;
}
