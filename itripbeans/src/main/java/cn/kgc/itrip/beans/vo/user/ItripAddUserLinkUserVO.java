package cn.kgc.itrip.beans.vo.user;

import lombok.Data;

/**
 * @author 杨幸运
 * @create 2020/10/7
 */
@Data
public class ItripAddUserLinkUserVO {
    private String linkIdCard;
    private Integer linkIdCardType;
    private String linkPhone;
    private String linkUserName;
    private Integer userId;
}
