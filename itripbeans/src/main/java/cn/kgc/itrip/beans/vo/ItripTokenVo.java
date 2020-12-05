package cn.kgc.itrip.beans.vo;

import lombok.Data;

/**
 * @author 杨幸运
 * @create 2020/9/21
 */
@Data
public class ItripTokenVo {
    /**
     * 到期时间
     */
    private Long expTime;
    /**
     * 生成时间
     */
    private Long genTime;
    /**
     * token
     */
    private String token;
}
