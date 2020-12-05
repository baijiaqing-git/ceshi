package cn.kgc.itrip.beans.vo.comment;

import lombok.Data;

/**
 * 返回前端-酒店各类评分VO
 */
@Data
public class ItripScoreCommentVO {
    /**
     * //点评查询页面酒店的位置得分
     */
    private Double avgPositionScore;

    /**
     * //点评查询页面酒店的设施得分
     */
    private Double avgFacilitiesScore;

    /**
     * //点评查询页面酒店的服务得分
     */
    private Double avgServiceScore;

    /**
     * //点评查询页面酒店的卫生得分
     */
    private Double avgHygieneScore;

    /**
     * //点评查询页面酒店的总体得分
     */
    private Double avgScore;

}
