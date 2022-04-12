package gmair.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gmair.dao.JpushRecordDao;
import gmair.dao.JpushUserDao;
import gmair.entity.JpushRecord;
import gmair.entity.JpushUser;
import gmair.service.MsgService;
import gmair.service.UserService;
import gmair.utils.InfoMapToUserEntity;
import gmair.utils.MyJPushClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private JpushUserDao jpushUserDao;

    @Autowired
    private JpushRecordDao jpushRecordDao;


    @Override
    public void saveUserInfo(Map<String, String> info) {
        //System.out.println(info);
        JpushUser user = InfoMapToUserEntity.infoMapToUserEntity(info);
        JpushUser isExist=jpushUserDao.selectByPrimaryKey(info.get("rid"));
        if(isExist==null){
            int result=jpushUserDao.insertSelective(user);
            System.out.println("用户信息插入成功: "+result);
        }else{
            int result=jpushUserDao.updateByPrimaryKeySelective(user);
            System.out.println("用户信息更新成功: "+result);
        }

    }


    @Override
    public synchronized void saveMsgClick(Map<String, String> info) {
        String msg_id=info.get("msg_id");
        String platform=info.get("platform");
        JpushRecord record=jpushRecordDao.selectByPrimaryKey(msg_id);
        if(platform.equals("Android"))
            record.setAndroidClicked(record.getAndroidClicked()+1);
        else
            record.setIosClicked(record.getIosClicked()+1);
        jpushRecordDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<JSONObject> getAllUserInfo() {
        List<JSONObject> result = new ArrayList<JSONObject>();
        for(JpushUser user:jpushUserDao.getAllJpushUserInfo()){
            result.add(user.toJson());
        }
        return result;
    }

    @Override
    public List<JSONObject> getUserInfoByPage(int page, Integer pageSize) {
        List<JSONObject> result = new ArrayList<JSONObject>();
        for(JpushUser user:jpushUserDao.getJpushUserInfoByPage(page,pageSize)){
            result.add(user.toJson());
        }
        return result;
    }

    @Override
    public List<String> getAllTags() {
        List<String> result = new ArrayList<>();
        result = jpushUserDao.getAllTags();
        return result;
    }



}
