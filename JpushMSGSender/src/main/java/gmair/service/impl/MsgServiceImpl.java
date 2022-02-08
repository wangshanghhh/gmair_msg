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
import gmair.utils.InfoMapToUserEntity;
import gmair.utils.MyJPushClient;
import gmair.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MyJPushClient jPushClient;

    @Autowired
    private JpushUserDao jpushUserDao;

    @Autowired
    private JpushRecordDao jpushRecordDao;

    final static int RidMaxNum=1000;

    @Override
    public int sendToAll(String notificationTitle, String notificationContent, String pic_url, String content, String timeToLive) {
        PushPayload pushPayload = PushPayload.newBuilder()
                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        // 指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notificationContent)
                                .setTitle(notificationTitle)
                                // 此字段为额外字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求
                                .addExtra("pic_url", pic_url)
                                .addExtra("text",content)
                                .build())
                        .build()
                )

                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(jPushClient.isApnsProduction())
                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(Integer.parseInt(timeToLive))
                        .build())
                .build();
        JpushRecord record =new JpushRecord("all",notificationTitle, notificationContent,content,pic_url,Integer.parseInt(timeToLive),"all");
        return sendPush(pushPayload,record);
    }

    //一次推送最多 RidMaxNum 个
    @Override
    public int sendByRid(List<String> ridsList, String notificationTitle, String notificationContent, String pic_url, String content, String timeToLive, String tagsList) {
        PushPayload pushPayload = PushPayload.newBuilder()
                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(ridsList))
                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        // 指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notificationContent)
                                .setTitle(notificationTitle)
                                // 此字段为额外字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求
                                .addExtra("pic_url", pic_url)
                                .addExtra("text",content)
                                .build())
                        .build()
                )
                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(jPushClient.isApnsProduction())
                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(Integer.parseInt(timeToLive))
                        .build())
                .build();
        JpushRecord record =new JpushRecord("all",notificationTitle, notificationContent,content,pic_url,Integer.parseInt(timeToLive),tagsList.equals("")?String.join(",",ridsList):tagsList);
        return sendPush(pushPayload, record);
    }

    @Override
    public void sendToAliasList(List<String> aliasList, String notificationTitle, String notificationContent, Map<String,String> extras) {
//        PushPayload pushPayload = PushPayload.newBuilder()
//                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.alias(aliasList))
//                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        // 指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                                .setAlert(notificationContent)
//                                .setTitle(notificationTitle)
//                                // 此字段为额外字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求
//                                .addExtra("pic_url", extras.get("pic_url"))
//                                .addExtra("text",extras.get("content"))
//                                .build())
//                        .build()
//                )
//                .setOptions(Options.newBuilder()
//                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        .setApnsProduction(jPushClient.isApnsProduction())
//                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                        .setTimeToLive(86400)
//                        .build())
//                .build();
//        sendPush(pushPayload, record);
    }


    @Override
    public int sendToTagsList(List<String> tagsList, String notificationTitle, String notificationContent,String pic_url, String content, String timeToLive) {
        List<String> rids=new ArrayList<String>();
        for(String tag:tagsList){
            rids.addAll(jpushUserDao.selectPrimaryKeyByTag(tag));
        }
        //System.out.println(rids);

        if(rids.size()<=RidMaxNum){
            return sendByRid(rids,notificationTitle,notificationContent,pic_url, content,timeToLive, String.join(",",tagsList));
        }
        else {
            //按rid每次能推送的最大数量推送
            int i = RidMaxNum;
            while (i < rids.size()) {
                sendByRid(rids.subList(i - RidMaxNum, i), notificationTitle, notificationContent, pic_url, content,timeToLive, String.join(",",tagsList));
                i += RidMaxNum;
            }
            return sendByRid(rids.subList(i, rids.size()), notificationTitle, notificationContent, pic_url, content,timeToLive, String.join(",",tagsList));
        }
    }

    //按tags推送已改为会员服务
//    @Override
//    public void sendToTagsList(List<String> tagsList, String notificationTitle, String notificationContent, Map<String,String> extras) {
//        PushPayload pushPayload = PushPayload.newBuilder()
//                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.tag(tagsList))
//                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        // 指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                                .setAlert(notificationContent)
//                                .setTitle(notificationTitle)
//                                // 此字段为额外字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求
//                                .addExtra("pic_url", extras.get("pic_url"))
//                                .addExtra("text",extras.get("content"))
//                                .build())
//                        .build()
//                )
//                .setOptions(Options.newBuilder()
//                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        .setApnsProduction(jPushClient.isApnsProduction())
//                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                        .setTimeToLive(86400)
//                        .build())
//                .build();
//        sendPush(pushPayload);
//    }

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
    public List<JSONObject> getSendRecords() {
        List<JSONObject> result = new ArrayList<JSONObject>();
        for(JpushRecord record:jpushRecordDao.getAllJpushRecordsWithoutStatistic()) {
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

    @Override
    public void saveMsgClick(Map<String, String> info) {
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
    public List<String> getAllTags() {
        List<String> result = new ArrayList<>();
        result = jpushUserDao.getAllTags();
        return result;
    }

    private int sendPush(PushPayload pushPayload, JpushRecord record) {
        //MyJPushClient.getLogger().info("pushPayload={}", pushPayload);
        PushResult pushResult = null;
        try {
            pushResult = jPushClient.getJPushClient().sendPush(pushPayload);
            //MyJPushClient.getLogger().info("" + pushResult);
            if (pushResult.getResponseCode() == MyJPushClient.getResponseOk()) {
                MyJPushClient.getLogger().info("push successful, pushPayload={}", pushPayload);
                record.setMsgId(String.valueOf(pushResult.msg_id));
                record.setIsSuccess((byte) 1);
            }
        } catch (APIConnectionException | APIRequestException e) {
            MyJPushClient.getLogger().error("push failed: pushPayload={}", pushPayload);
            //System.out.println(e.getMessage());
            Map<String, String> detail = new HashMap<>();
            //String转map
            for(String key: JSON.parseObject(e.getMessage()).keySet()){
                detail.put(key,JSON.parseObject(e.getMessage()).getString(key));
            }
            record.setMsgId(String.valueOf(detail.get("msg_id")));
            record.setIsSuccess((byte) 0);
            record.setFailDetail(detail.get("error"));
        }
        record.setPushTime(new Date());
        jpushRecordDao.insertSelective(record);
        return pushResult==null?0:1;
    }


}
