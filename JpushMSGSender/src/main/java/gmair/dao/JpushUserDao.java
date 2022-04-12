package gmair.dao;

import gmair.entity.JpushUser;

import java.util.Collection;
import java.util.List;

public interface JpushUserDao {
    int deleteByPrimaryKey(String rid);

    int insert(JpushUser record);

    int insertSelective(JpushUser record);

    JpushUser selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(JpushUser record);

    int updateByPrimaryKey(JpushUser record);

    List<JpushUser> getAllJpushUserInfo();

    List<String> selectPrimaryKeyByTag(String tag);

    List<String> getAllTags();


    List<JpushUser> getJpushUserInfoByPage(int page, Integer pageSize);
}