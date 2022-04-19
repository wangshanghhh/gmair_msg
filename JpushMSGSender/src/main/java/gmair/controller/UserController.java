package gmair.controller;

import com.alibaba.fastjson.JSONObject;
import gmair.service.MsgService;
import gmair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    //查询所有的用户标签
    @GetMapping("/ getAllTags")
    List<String> getAllTags(){
        return userService.getAllTags();
    }

    //保存推送用户的个人信息
    @PostMapping("/saveUserInfo")
    void saveUserInfo(@RequestParam Map<String,String> info){
        userService.saveUserInfo(info);
    }

    //查询全部推送用户的个人信息
    @GetMapping("/getAllUserInfo")
    List<JSONObject> getAllUserInfo(){
        return userService.getAllUserInfo();
    }

    //分页查询全部推送用户的个人信息
    @GetMapping("/getUserInfoByPage")
    List<JSONObject> getUserInfoByPage(@RequestParam(required=true) int page,@RequestParam(required=false) Integer pageSize){
        if(pageSize==null){
            pageSize = 8;
        }
        return userService.getUserInfoByPage(page,pageSize);
    }

    //用户点击后更新推送记录
    @PostMapping("/saveMsgClick")
    void getReceivedInfo(@RequestParam Map<String,String> info){
        userService.saveMsgClick(info);
    }
}
