package com.hoolai.chatmonitor.open.dao.mybatis.mapper;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;


public interface AdminGroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    long countByExample(AdminGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int deleteByExample(AdminGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer groupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int insert(AdminGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int insertSelective(AdminGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    List<AdminGroup> selectByExample(AdminGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    AdminGroup selectByPrimaryKey(Integer groupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AdminGroup record, @Param("example") AdminGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AdminGroup record, @Param("example") AdminGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AdminGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_group
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AdminGroup record);


	       ////*******自定义开始********/
        //***********自定义结束****////
}
