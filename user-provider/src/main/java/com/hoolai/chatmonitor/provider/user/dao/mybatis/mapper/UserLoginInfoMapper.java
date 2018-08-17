package com.hoolai.chatmonitor.provider.user.dao.mybatis.mapper;

import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserLoginInfo;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserLoginInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;


public interface UserLoginInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    long countByExample(UserLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int deleteByExample(UserLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int insert(UserLoginInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int insertSelective(UserLoginInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    List<UserLoginInfo> selectByExample(UserLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    UserLoginInfo selectByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UserLoginInfo record, @Param("example") UserLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UserLoginInfo record, @Param("example") UserLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserLoginInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_login_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserLoginInfo record);


	       ////*******自定义开始********/
        //***********自定义结束****////
}
