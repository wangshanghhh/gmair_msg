package gmair.utils;

import gmair.entity.JpushUser;

import java.util.Map;
import java.util.Set;

public class InfoMapToUserEntity {
    public static JpushUser infoMapToUserEntity(Map<String, String> info){
        JpushUser user;
        Set<String> keys=info.keySet();
        if(keys.contains("alias")){
            user=new JpushUser(info.get("rid"),info.get("alias"),
                    info.get("tags"), (byte) (info.get("isVisitor").equals("true") ?1:0),
                    info.get("userInfo[consumerId]"),info.get("userInfo[name]"),
                    info.get("userInfo[address]"),info.get("userInfo[province]"),
                    info.get("userInfo[city]"),info.get("userInfo[phone]"),info.get("platform"));
        }else{
            user=new JpushUser(info.get("rid"),info.get("tags"),
                    (byte) (info.get("isVisitor").equals("true") ?1:0),info.get("platform"));
        }
        return user;
    }
}
