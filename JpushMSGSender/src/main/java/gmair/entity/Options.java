package gmair.entity;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Options implements PushModel {
    private static final String SENDNO = "sendno";
    private static final String OVERRIDE_MSG_ID = "override_msg_id";
    private static final String TIME_TO_LIVE = "time_to_live";
    private static final String APNS_PRODUCTION = "apns_production";
    private static final String BIG_PUSH_DURATION = "big_push_duration";
    private static final String APNS_COLLAPSE_ID = "apns_collapse_id";
    private static final long NONE_TIME_TO_LIVE = -1L;
    private final int sendno;
    private final long overrideMsgId;
    private long timeToLive;
    private boolean apnsProduction;
    private int bigPushDuration;
    private String apnsCollapseId;

    private Options(int sendno, long overrideMsgId, long timeToLive, boolean apnsProduction, int bigPushDuration, String apnsCollapseId) {
        this.sendno = sendno;
        this.overrideMsgId = overrideMsgId;
        this.timeToLive = timeToLive;
        this.apnsProduction = apnsProduction;
        this.bigPushDuration = bigPushDuration;
        this.apnsCollapseId = apnsCollapseId;
    }

    public static cn.jpush.api.push.model.Options.Builder newBuilder() {
        return new cn.jpush.api.push.model.Options.Builder();
    }

    public static cn.jpush.api.push.model.Options sendno() {
        return newBuilder().setSendno(ServiceHelper.generateSendno()).build();
    }

    public static cn.jpush.api.push.model.Options sendno(int sendno) {
        return newBuilder().setSendno(sendno).build();
    }

    public void setApnsProduction(boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setBigPushDuration(int bigPushDuration) {
        this.bigPushDuration = bigPushDuration;
    }

    public int getSendno() {
        return this.sendno;
    }

    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (this.sendno > 0) {
            json.add("sendno", new JsonPrimitive(this.sendno));
        }

        if (this.overrideMsgId > 0L) {
            json.add("override_msg_id", new JsonPrimitive(this.overrideMsgId));
        }

        if (this.timeToLive >= 0L) {
            json.add("time_to_live", new JsonPrimitive(this.timeToLive));
        }

        json.add("apns_production", new JsonPrimitive(this.apnsProduction));
        if (this.bigPushDuration > 0) {
            json.add("big_push_duration", new JsonPrimitive(this.bigPushDuration));
        }

        if (this.apnsCollapseId != null) {
            json.add("apns_collapse_id", new JsonPrimitive(this.apnsCollapseId));
        }

        return json;
    }

    public static class Builder {
        private int sendno = 0;
        private long overrideMsgId = 0L;
        private long timeToLive = -1L;
        private boolean apnsProduction = false;
        private int bigPushDuration = 0;
        private String apnsCollapseId;

        public Builder() {
        }

//        public cn.jpush.api.push.model.Options.Builder setSendno(int sendno) {
//            this.sendno = sendno;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Options.Builder setOverrideMsgId(long overrideMsgId) {
//            this.overrideMsgId = overrideMsgId;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Options.Builder setTimeToLive(long timeToLive) {
//            this.timeToLive = timeToLive;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Options.Builder setApnsProduction(boolean apnsProduction) {
//            this.apnsProduction = apnsProduction;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Options.Builder setApnsCollapseId(String id) {
//            this.apnsCollapseId = id;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Options.Builder setBigPushDuration(int bigPushDuration) {
//            this.bigPushDuration = bigPushDuration;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Options build() {
//            Preconditions.checkArgument(this.sendno >= 0, "sendno should be greater than 0.");
//            Preconditions.checkArgument(this.overrideMsgId >= 0L, "override_msg_id should be greater than 0.");
//            Preconditions.checkArgument(this.timeToLive >= -1L, "time_to_live should be greater than 0.");
//            Preconditions.checkArgument(this.bigPushDuration >= 0, "bigPushDuration should be greater than 0.");
//            if (this.sendno <= 0) {
//                this.sendno = ServiceHelper.generateSendno();
//            }
//
//            return new cn.jpush.api.push.model.Options(this.sendno, this.overrideMsgId, this.timeToLive, this.apnsProduction, this.bigPushDuration, this.apnsCollapseId);
//        }
    }
}

