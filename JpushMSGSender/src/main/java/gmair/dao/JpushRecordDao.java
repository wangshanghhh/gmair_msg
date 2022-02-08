package gmair.dao;

import gmair.entity.JpushRecord;

import java.util.List;

public interface JpushRecordDao {
    int deleteByPrimaryKey(String msgId);

    int insert(JpushRecord record);

    int insertSelective(JpushRecord record);

    JpushRecord selectByPrimaryKey(String msgId);

    int updateByPrimaryKeySelective(JpushRecord record);

    int updateByPrimaryKey(JpushRecord record);

    List<JpushRecord> getAllJpushRecordsWithoutStatistic();
}