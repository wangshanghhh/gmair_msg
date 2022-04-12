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
import gmair.service.RecordService;
import gmair.utils.InfoMapToUserEntity;
import gmair.utils.MyJPushClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private MyJPushClient jPushClient;


    @Autowired
    private JpushRecordDao jpushRecordDao;


    @Override
    public List<JSONObject> getSendRecords() {
        List<JSONObject> result = new ArrayList<JSONObject>();
        for(JpushRecord record:jpushRecordDao.getAllJpushRecordsWithoutStatistic()) {
            result.add(record.toJsonWithoutStatistic());
        }
        return result;
    }


    @Override
    public List<JSONObject> getSendRecordsByPage(int page, Integer pageSize) {
        List<JSONObject> result = new ArrayList<JSONObject>();
        for(JpushRecord record:jpushRecordDao.getJpushRecordsByPageWithoutStatistic(page,pageSize)) {
            result.add(record.toJsonWithoutStatistic());
        }
        return result;
    }

    @Override
    public JSONObject getReceivedInfo(String msg_id){
        JpushRecord record=null;
        try {
            ReceivedsResult receivedsResult = jPushClient.getJPushClient().getReportReceiveds(msg_id);
            record=jpushRecordDao.selectByPrimaryKey(msg_id);
            ReceivedsResult.Received received= receivedsResult.received_list.get(0);
            record.setAndroidReceived(received.android_received);
            record.setIosReceived(received.ios_msg_received);
            jpushRecordDao.updateByPrimaryKeySelective(record);

        } catch (APIConnectionException | APIRequestException e) {
            MyJPushClient.getLogger().error("get ReceivedInfo failed: {}", e.getMessage());
        }
        return record!=null?record.toJsonStatistic():new JSONObject();
    }



}
