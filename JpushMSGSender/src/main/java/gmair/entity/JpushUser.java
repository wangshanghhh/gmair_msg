package gmair.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JpushUser {
    private String rid;

    private String alias;

    private String tags;

    private Byte isvisitor;

    private String consumerid;

    private String name;

    private String address;

    private String province;

    private String city;

    private String phone;

    private String platform;

    public JpushUser(){}

    public JpushUser(String rid, String alias, String tags, Byte isvisitor, String consumerid, String name, String address, String province, String city, String phone, String platform) {
        this.rid = rid;
        this.alias = alias;
        this.tags = tags;
        this.isvisitor = isvisitor;
        this.consumerid = consumerid;
        this.name = name;
        this.address = address;
        this.province = province;
        this.city = city;
        this.phone = phone;
        this.platform = platform;
    }

    public JpushUser(String rid, String tags,Byte isvisitor, String platform) {
        this.rid = rid;
        this.tags = tags;
        this.isvisitor = isvisitor;
        this.platform = platform;
    }

    public JSONObject toJson() {
        Map<String,Object> result=new HashMap<>();
        result.put("rid",rid);
        result.put("alias",alias);
        result.put("tags",tags);
        result.put("isvisitor",isvisitor);
        result.put("consumerid",consumerid);
        result.put("name",name);
        result.put("address",address);
        result.put("province",province);
        result.put("city",city);
        result.put("phone",phone);
        result.put("platform",platform);
        return new JSONObject(result);
    }


    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Byte getIsvisitor() {
        return isvisitor;
    }

    public void setIsvisitor(Byte isvisitor) {
        this.isvisitor = isvisitor;
    }

    public String getConsumerid() {
        return consumerid;
    }

    public void setConsumerid(String consumerid) {
        this.consumerid = consumerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


}