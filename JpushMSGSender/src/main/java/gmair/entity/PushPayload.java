package gmair.entity;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class PushPayload implements PushModel {
    private static final String PLATFORM = "platform";
    private static final String AUDIENCE = "audience";
    private static final String NOTIFICATION = "notification";
    private static final String MESSAGE = "message";
    private static final String OPTIONS = "options";
    private static final String SMS = "sms_message";
    private static final String CID = "cid";
    private static final int MAX_GLOBAL_ENTITY_LENGTH = 4000;
    private static final int MAX_IOS_PAYLOAD_LENGTH = 2000;
    private static Gson _gson = (new GsonBuilder()).disableHtmlEscaping().create();
    private final Platform platform;
    private final Audience audience;
    private final Notification notification;
    private final Message message;
    private Options options;
    private cn.jpush.api.push.model.SMS sms;
    private String cid;

    private PushPayload(Platform platform, Audience audience, Notification notification, Message message, Options options, SMS sms, String cid) {
        this.platform = platform;
        this.audience = audience;
        this.notification = notification;
        this.message = message;
        this.options = options;
        this.sms = sms;
        this.cid = cid;
    }

    public static cn.jpush.api.push.model.PushPayload.Builder newBuilder() {
        return new cn.jpush.api.push.model.PushPayload.Builder();
    }

    public static cn.jpush.api.push.model.PushPayload alertAll(String alert) {
        return (new cn.jpush.api.push.model.PushPayload.Builder()).setPlatform(Platform.all()).setAudience(Audience.all()).setNotification(Notification.alert(alert)).build();
    }

    public static cn.jpush.api.push.model.PushPayload alertAll(String alert, SMS sms) {
        return (new cn.jpush.api.push.model.PushPayload.Builder()).setPlatform(Platform.all()).setAudience(Audience.all()).setNotification(Notification.alert(alert)).setSMS(sms).build();
    }

    public static cn.jpush.api.push.model.PushPayload messageAll(String msgContent) {
        return (new cn.jpush.api.push.model.PushPayload.Builder()).setPlatform(Platform.all()).setAudience(Audience.all()).setMessage(Message.content(msgContent)).build();
    }

    public static cn.jpush.api.push.model.PushPayload messageAll(String msgContent, SMS sms) {
        return (new cn.jpush.api.push.model.PushPayload.Builder()).setPlatform(Platform.all()).setAudience(Audience.all()).setMessage(Message.content(msgContent)).setSMS(sms).build();
    }

    public static cn.jpush.api.push.model.PushPayload fromJSON(String payloadString) {
        return (cn.jpush.api.push.model.PushPayload)_gson.fromJson(payloadString, cn.jpush.api.push.model.PushPayload.class);
    }

//    public void resetOptionsApnsProduction(boolean apnsProduction) {
//        if (null == this.options) {
//            this.options = Options.newBuilder().setApnsProduction(apnsProduction).build();
//        } else {
//            this.options.setApnsProduction(apnsProduction);
//        }
//
//    }
//
//    public void resetOptionsTimeToLive(long timeToLive) {
//        if (null == this.options) {
//            this.options = Options.newBuilder().setTimeToLive(timeToLive).build();
//        } else {
//            this.options.setTimeToLive(timeToLive);
//        }
//
//    }

    public int getSendno() {
        return null != this.options ? this.options.getSendno() : 0;
    }

    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != this.platform) {
            json.add("platform", this.platform.toJSON());
        }

        if (null != this.audience) {
            json.add("audience", this.audience.toJSON());
        }

        if (null != this.notification) {
            json.add("notification", this.notification.toJSON());
        }

        if (null != this.message) {
            json.add("message", this.message.toJSON());
        }

        if (null != this.options) {
            json.add("options", this.options.toJSON());
        }

        if (null != this.sms) {
            json.add("sms_message", this.sms.toJSON());
        }

        if (null != this.cid) {
            json.addProperty("cid", this.cid);
        }

        return json;
    }

    public boolean isGlobalExceedLength() {
        int messageLength = 0;
        JsonObject payload = (JsonObject)this.toJSON();
        JsonObject notification;
        if (payload.has("message")) {
            notification = payload.getAsJsonObject("message");
            messageLength = notification.toString().getBytes().length;
        }

        if (!payload.has("notification")) {
            return messageLength > 4000;
        } else {
            notification = payload.getAsJsonObject("notification");
            if (notification.has("android")) {
                JsonObject android = notification.getAsJsonObject("android");
                int androidLength = android.toString().getBytes().length;
                return androidLength + messageLength > 4000;
            } else {
                return false;
            }
        }
    }

    public boolean isIosExceedLength() {
        JsonObject payload = (JsonObject)this.toJSON();
        if (payload.has("notification")) {
            JsonObject notification = payload.getAsJsonObject("notification");
            if (notification.has("ios")) {
                JsonObject ios = notification.getAsJsonObject("ios");
                return ios.toString().getBytes().length > 2000;
            }

            if (notification.has("alert")) {
                String alert = notification.get("alert").getAsString();
                JsonObject ios = new JsonObject();
                ios.add("alert", new JsonPrimitive(alert));
                return ios.toString().getBytes().length > 2000;
            }
        }

        return false;
    }

    public String toString() {
        return _gson.toJson(this.toJSON());
    }

//    public static class Builder {
//        private Platform platform = null;
//        private Audience audience = null;
//        private Notification notification = null;
//        private Message message = null;
//        private Options options = null;
//        private SMS sms = null;
//        private String cid;
//
//        public Builder() {
//        }
//
//        public cn.jpush.api.push.model.PushPayload.Builder setPlatform(Platform platform) {
//            this.platform = platform;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.PushPayload.Builder setAudience(Audience audience) {
//            this.audience = audience;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.PushPayload.Builder setNotification(Notification notification) {
//            this.notification = notification;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.PushPayload.Builder setMessage(Message message) {
//            this.message = message;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.PushPayload.Builder setOptions(Options options) {
//            this.options = options;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.PushPayload.Builder setSMS(SMS sms) {
//            this.sms = sms;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.PushPayload.Builder setCid(String cid) {
//            this.cid = cid;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.PushPayload build() {
//            Preconditions.checkArgument(null != this.audience && null != this.platform, "audience and platform both should be set.");
//            Preconditions.checkArgument(null != this.notification || null != this.message, "notification or message should be set at least one.");
//            if (null == this.options) {
//                this.options = Options.sendno();
//            }
//
//            return new cn.jpush.api.push.model.PushPayload(this.platform, this.audience, this.notification, this.message, this.options, this.sms, this.cid);
//        }
//    }
}
