package gmair.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface UserService {


    void saveUserInfo(Map<String, String> info);

    void saveMsgClick(Map<String, String> info);

    List<JSONObject> getAllUserInfo();

    List<String> getAllTags();


    List<JSONObject> getUserInfoByPage(int page, Integer pageSize);
}
