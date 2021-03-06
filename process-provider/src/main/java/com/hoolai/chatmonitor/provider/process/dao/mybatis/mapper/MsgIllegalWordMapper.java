package com.hoolai.chatmonitor.provider.process.dao.mybatis.mapper;

import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgIllegalWord;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgIllegalWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;


public interface MsgIllegalWordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    long countByExample(MsgIllegalWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int deleteByExample(MsgIllegalWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int insert(MsgIllegalWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int insertSelective(MsgIllegalWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    List<MsgIllegalWord> selectByExample(MsgIllegalWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    MsgIllegalWord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MsgIllegalWord record, @Param("example") MsgIllegalWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MsgIllegalWord record, @Param("example") MsgIllegalWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MsgIllegalWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_words
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MsgIllegalWord record);


	       ////*******自定义开始********/
        //***********自定义结束****////
}
