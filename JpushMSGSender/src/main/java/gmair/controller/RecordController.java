package gmair.controller;

import com.alibaba.fastjson.JSONObject;
import gmair.service.MsgService;
import gmair.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    //查询全部推送记录(不包括统计信息)
    @GetMapping("/getSendRecords")
    List<JSONObject> getSendRecords(){
        return recordService.getSendRecords();
    }


    //分页查询全部推送记录(不包括统计信息)
    @GetMapping("/getSendRecordsByPage")
    List<JSONObject> getSendRecordsByPage(@RequestParam(required=true) int page,@RequestParam(required=false) Integer pageSize){
        if(pageSize==null){
            pageSize = 8;
        }
        return recordService.getSendRecordsByPage(page,pageSize);
    }

    //获取消息送达及点击等统计信息
    @GetMapping("/getReceivedInfo")
    JSONObject getReceivedInfo(String msg_id){
        return recordService.getReceivedInfo(msg_id);
    }


}
