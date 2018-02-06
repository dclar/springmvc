package org.dclar.mybatis.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.dclar.mybatis.model.ActEvtLog;
import org.dclar.mybatis.model.ActEvtLogExample;

public interface ActEvtLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    long countByExample(ActEvtLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int deleteByExample(ActEvtLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int deleteByPrimaryKey(Long logNr);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int insert(ActEvtLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int insertSelective(ActEvtLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    List<ActEvtLog> selectByExampleWithBLOBs(ActEvtLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    List<ActEvtLog> selectByExample(ActEvtLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    ActEvtLog selectByPrimaryKey(Long logNr);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int updateByExampleSelective(@Param("record") ActEvtLog record, @Param("example") ActEvtLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") ActEvtLog record, @Param("example") ActEvtLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int updateByExample(@Param("record") ActEvtLog record, @Param("example") ActEvtLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int updateByPrimaryKeySelective(ActEvtLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(ActEvtLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACT_EVT_LOG
     *
     * @mbg.generated Wed Jan 17 18:14:29 CST 2018
     */
    int updateByPrimaryKey(ActEvtLog record);
}