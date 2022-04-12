package gmair.dao.impl;

import gmair.dao.BaseDao;
import gmair.dao.JpushRecordDao;
import gmair.entity.JpushRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JpushRecordDaoImpl extends BaseDao implements JpushRecordDao {
    @Override
    public int deleteByPrimaryKey(String msgId) {
        return 0;
    }

    @Override
    public int insert(JpushRecord record) {
        return 0;
    }

    @Override
    public int insertSelective(JpushRecord record) {
        int result =sqlSession.insert("gmair.dao.JpushRecordDao.insertSelective",record);
        return result;
    }

    @Override
    public JpushRecord selectByPrimaryKey(String msgId) {
        JpushRecord result=sqlSession.selectOne("gmair.dao.JpushRecordDao.selectByPrimaryKey",msgId);
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(JpushRecord record) {
        int result =sqlSession.update("gmair.dao.JpushRecordDao.updateByPrimaryKeySelective",record);
        return result;

    }

    @Override
    public int updateByPrimaryKey(JpushRecord record) {
        return 0;
    }

    @Override
    public List<JpushRecord> getAllJpushRecordsWithoutStatistic() {
        List<JpushRecord> result=new ArrayList<>();
        result=sqlSession.selectList("gmair.dao.JpushRecordDao.getAllJpushRecordsWithoutStatistic");
        return result;
    }

    @Override
    public List<JpushRecord> getJpushRecordsByPageWithoutStatistic(int page, Integer pageSize) {
        List<JpushRecord> result=new ArrayList<>();
        Map<String,Integer> params =  new HashMap<String,Integer>();
        params.put("start",(page-1)*pageSize);
        params.put("nums",pageSize);
        result=sqlSession.selectList("gmair.dao.JpushRecordDao.getJpushRecordsByPageWithoutStatistic",params);
        return result;
    }


}
