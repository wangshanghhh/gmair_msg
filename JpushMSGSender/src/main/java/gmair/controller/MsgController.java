package gmair.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gmair.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgService msgService;

    @GetMapping("/test")
    String test() {
        return "如果您希望一条推送能发给您 App 下的全量用户，建议您使用“广播”\n" +
                "如果您希望一条推送能批量推送给具有相同属性的一群用户，建议您使用“标签（tag）”推送\n" +
                "如果您希望一条推送只针对指定的某一个用户推送，则您可以选择“别名（alias）”或者“registration_id”推送";
    }

    //广播推送
    @PostMapping("/sendToAll")
    int sendToAll(String notificationTitle, String notificationContent, @RequestParam(required=false) String pic_url,@RequestParam(required=false) String content,String timeToLive){
        return msgService.sendToAll(notificationTitle, notificationContent, pic_url,content,timeToLive);
    }

    //指定设备ID推送
    @PostMapping("/sendByRid")
    int sendByRid(@RequestParam List<String> ridsList, String notificationTitle, String notificationContent, @RequestParam(required=false) String pic_url,@RequestParam(required=false) String content,String timeToLive){
        return msgService.sendByRid(ridsList, notificationTitle, notificationContent, pic_url,content,timeToLive,"");
    }
//
//    //指定设备别名(用户ID)推送
//    @PostMapping("/sendToAliasList")
//    void sendToAliasList(@RequestParam List<String> aliasList, String notificationTitle, String notificationContent, @RequestParam(required=false) String extras){
//        Map<String, String> extrasInfo = new HashMap<>();
//        for(String key:JSON.parseObject(extras).keySet()){
//            extrasInfo.put(key,JSON.parseObject(extras).getString(key));
//        }
//        msgService.sendToAliasList(aliasList, notificationTitle, notificationContent, extrasInfo);
//    }
//
    //指定同一标签的用户推送
    @PostMapping("/sendToTagsList")
    int sendToTagsList(@RequestParam List<String> tagsList, String notificationTitle, String notificationContent, @RequestParam(required=false) String pic_url,@RequestParam(required=false) String content,String timeToLive){
        return msgService.sendToTagsList(tagsList, notificationTitle, notificationContent, pic_url,content,timeToLive);
    }

    //查询所有的用户标签
    @GetMapping("/ getAllTags")
    List<String> getAllTags(){
        return msgService.getAllTags();
    }

    //保存推送用户的个人信息
    @PostMapping("/saveUserInfo")
    void saveUserInfo(@RequestParam Map<String,String> info){
        msgService.saveUserInfo(info);
    }

    //查询全部推送用户的个人信息
    @GetMapping("/getAllUserInfo")
    List<JSONObject> getAllUserInfo(){
        return msgService.getAllUserInfo();
    }

    //查询全部推送记录(不包括统计信息)
    @GetMapping("/getSendRecords")
    List<JSONObject> getSendRecords(){
        return msgService.getSendRecords();
    }

    //获取消息送达及点击等统计信息
    @GetMapping("/getReceivedInfo")
    JSONObject getReceivedInfo(String msg_id){
        return msgService.getReceivedInfo(msg_id);
    }

    //用户点击推送记录
    @PostMapping("/saveMsgClick")
    void getReceivedInfo(@RequestParam Map<String,String> info){
        msgService.saveMsgClick(info);
    }
}
