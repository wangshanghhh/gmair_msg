package gmair.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface MsgService {

    int sendToAll(String notificationTitle, String notificationContent, String pic_url, String content, String timeToLive);

    int sendByRid(List<String> ridsList, String notificationTitle, String notificationContent, String pic_url, String content, String timeToLive, String tagsList);

    void sendToAliasList(List<String> aliasList, String notificationTitle, String notificationContent, Map<String,String> extras);

    int sendToTagsList(List<String> tagsList, String notificationTitle, String notificationContent, String pic_url, String content, String timeToLive);


}
