package gmair.dao.impl;

import gmair.dao.BaseDao;
import gmair.dao.JpushUserDao;
import gmair.entity.JpushUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JpushUserDaoImpl extends BaseDao implements JpushUserDao {
    @Override
    public int deleteByPrimaryKey(String rid) {
        return 0;
    }

    @Override
    public int insert(JpushUser record) {
        return 0;
    }

    @Override
    public int insertSelective(JpushUser record) {
        int result =sqlSession.insert("gmair.dao.JpushUserDao.insertSelective",record);
        return result;
    }

    @Override
    public JpushUser selectByPrimaryKey(String rid) {
        JpushUser user=sqlSession.selectOne("gmair.dao.JpushUserDao.selectByPrimaryKey",rid);
        return user;
    }

    @Override
    public int updateByPrimaryKeySelective(JpushUser record) {
        int result =sqlSession.update("gmair.dao.JpushUserDao.updateByPrimaryKeySelective",record);
        return result;
    }

    @Override
    public int updateByPrimaryKey(JpushUser record) {
        return 0;
    }

    @Override
    public List<JpushUser> getAllJpushUserInfo() {
        List<JpushUser> result = new ArrayList<>();
        result=sqlSession.selectList("gmair.dao.JpushUserDao.getAllJpushUserInfo");
        return result;
    }

    @Override
    public List<String> selectPrimaryKeyByTag(String tag) {
        List<String> result = new ArrayList<>();
        result=sqlSession.selectList("gmair.dao.JpushUserDao.selectPrimaryKeyByTag",tag);
        return result;
    }

    @Override
    public List<String> getAllTags() {
        List<String> result = new ArrayList<>();
        result = sqlSession.selectList("gmair.dao.JpushUserDao.getAllTags");
        return result;
    }
}
