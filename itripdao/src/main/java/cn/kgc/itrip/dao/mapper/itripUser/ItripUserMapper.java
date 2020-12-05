package cn.kgc.itrip.dao.mapper.itripUser;

import cn.kgc.itrip.beans.model.ItripUser;
import org.apache.ibatis.annotations.Param;

public interface ItripUserMapper {

    public Integer queryUserCode(String userCode);

    public ItripUser queryUserByUserCodeAndUserPassword(@Param("userCode") String userCode,
                                                        @Param("password") String password);

    /**
     * 更新用户平台标识IP
     * @param userId
     * @return
     */
    public Integer updataUserFlatId(Integer userId);

    /**
     * 根据用户名查询用户信息
     * @param userCode 用户名
     * @return
     */
    public ItripUser queryUserByUserCode(String userCode);

    /**
     * 根据用户名删除用户信息
     * @param userCode 用户名
     * @return 受影响的行数
     */
    public Integer deleteUserByUserCode(String userCode);

    /**
     * 更改用户状态
     * @param userCode
     * @return
     */
    public Integer updateUserActive(String userCode);

    public Integer insertItripUser(ItripUser itripUser) throws Exception;

}
