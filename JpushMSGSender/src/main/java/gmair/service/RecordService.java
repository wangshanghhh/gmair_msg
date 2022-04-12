package gmair.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface RecordService {


    List<JSONObject> getSendRecords();

    List<JSONObject> getSendRecordsByPage(int page, Integer pageSize);

    JSONObject getReceivedInfo(String msg_id);

}
