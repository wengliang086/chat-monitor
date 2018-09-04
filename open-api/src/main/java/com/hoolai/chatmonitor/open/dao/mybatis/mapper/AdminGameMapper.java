package com.hoolai.chatmonitor.open.dao.mybatis.mapper;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;


public interface AdminGameMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    long countByExample(AdminGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int deleteByExample(AdminGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long gameId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int insert(AdminGame record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int insertSelective(AdminGame record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    List<AdminGame> selectByExample(AdminGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    AdminGame selectByPrimaryKey(Long gameId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AdminGame record, @Param("example") AdminGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AdminGame record, @Param("example") AdminGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AdminGame record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_game
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AdminGame record);


	       ////*******自定义开始********/
        //***********自定义结束****////
}
