package gmair.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JpushRecord {
    private String msgId;

    private String platform;

    private String notiTitle;

    private String notiContent;

    private String extrasText;

    private String extrasPicUrl;

    private Integer timeToLive;

    private Byte isSuccess;

    private String failDetail;

    private Date pushTime;

    private Integer androidReceived;

    private Integer androidClicked;

    private Integer iosReceived;

    private Integer iosClicked;

    private String audience;

    JpushRecord(){}

    public JpushRecord(String platform, String notiTitle, String notiContent, String extrasText, String extrasPicUrl, Integer timeToLive, String audience) {
        this.platform = platform;
        this.notiTitle = notiTitle;
        this.notiContent = notiContent;
        this.extrasText = extrasText;
        this.extrasPicUrl = extrasPicUrl;
        this.timeToLive = timeToLive;
        this.audience = audience;
    }

    public JSONObject toJsonWithoutStatistic() {
        Map<String,Object> result=new HashMap<>();
        result.put("msgId",msgId);
        result.put("platform",platform);
        result.put("audience",audience);
        result.put("notiTitle",notiTitle);
        result.put("notiContent",notiContent);
        result.put("extrasText",extrasText);
        result.put("extrasPicUrl",extrasPicUrl);
        result.put("pushTime",pushTime.toString());
        result.put("isSuccess",isSuccess);
        result.put("failDetail",failDetail);
        result.put("timeToLive",timeToLive);
        return new JSONObject(result);
    }

    public JSONObject toJsonStatistic() {
        Map<String,Object> result=new HashMap<>();
        result.put("msgId",msgId);
        result.put("androidReceived",androidReceived);
        result.put("androidClicked",androidClicked);
        result.put("iosReceived",iosReceived);
        result.put("iosClicked",iosClicked);
        result.put("pushTime",pushTime.toString());
        result.put("timeToLive",timeToLive);
        return new JSONObject(result);
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotiTitle() {
        return notiTitle;
    }

    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public String getNotiContent() {
        return notiContent;
    }

    public void setNotiContent(String notiContent) {
        this.notiContent = notiContent;
    }

    public String getExtrasText() {
        return extrasText;
    }

    public void setExtrasText(String extrasText) {
        this.extrasText = extrasText;
    }

    public String getExtrasPicUrl() {
        return extrasPicUrl;
    }

    public void setExtrasPicUrl(String extrasPicUrl) {
        this.extrasPicUrl = extrasPicUrl;
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Byte getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getFailDetail() {
        return failDetail;
    }

    public void setFailDetail(String failDetail) {
        this.failDetail = failDetail;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getAndroidReceived() {
        return androidReceived;
    }

    public void setAndroidReceived(Integer androidReceived) {
        this.androidReceived = androidReceived;
    }

    public Integer getAndroidClicked() {
        return androidClicked;
    }

    public void setAndroidClicked(Integer androidClicked) {
        this.androidClicked = androidClicked;
    }

    public Integer getIosReceived() {
        return iosReceived;
    }

    public void setIosReceived(Integer iosReceived) {
        this.iosReceived = iosReceived;
    }

    public Integer getIosClicked() {
        return iosClicked;
    }

    public void setIosClicked(Integer iosClicked) {
        this.iosClicked = iosClicked;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }


}