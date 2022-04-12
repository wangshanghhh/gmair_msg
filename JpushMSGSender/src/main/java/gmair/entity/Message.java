package gmair.entity;

import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Message implements PushModel {
    private static final String TITLE = "title";
    private static final String MSG_CONTENT = "msg_content";
    private static final String CONTENT_TYPE = "content_type";
    private static final String EXTRAS = "extras";
    private final String title;
    private final String msgContent;
    private final String contentType;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;

    private Message(String title, String msgContent, String contentType, Map<String, String> extras, Map<String, Number> numberExtras, Map<String, Boolean> booleanExtras, Map<String, JsonObject> jsonExtras) {
        this.title = title;
        this.msgContent = msgContent;
        this.contentType = contentType;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
    }

    public static cn.jpush.api.push.model.Message.Builder newBuilder() {
        return new cn.jpush.api.push.model.Message.Builder();
    }

    public static cn.jpush.api.push.model.Message content(String msgContent) {
        return (new cn.jpush.api.push.model.Message.Builder()).setMsgContent(msgContent).build();
    }

    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != this.title) {
            json.add("title", new JsonPrimitive(this.title));
        }

        if (null != this.msgContent) {
            json.add("msg_content", new JsonPrimitive(this.msgContent));
        }

        if (null != this.contentType) {
            json.add("content_type", new JsonPrimitive(this.contentType));
        }

        JsonObject extrasObject = null;
        if (null != this.extras || null != this.numberExtras || null != this.booleanExtras || null != this.jsonExtras) {
            extrasObject = new JsonObject();
        }

        Iterator var3;
        String key;
        if (null != this.extras) {
            var3 = this.extras.keySet().iterator();

            while(var3.hasNext()) {
                key = (String)var3.next();
                if (this.extras.get(key) != null) {
                    extrasObject.add(key, new JsonPrimitive((String)this.extras.get(key)));
                } else {
                    extrasObject.add(key, JsonNull.INSTANCE);
                }
            }
        }

        if (null != this.numberExtras) {
            var3 = this.numberExtras.keySet().iterator();

            while(var3.hasNext()) {
                key = (String)var3.next();
                extrasObject.add(key, new JsonPrimitive((Number)this.numberExtras.get(key)));
            }
        }

        if (null != this.booleanExtras) {
            var3 = this.booleanExtras.keySet().iterator();

            while(var3.hasNext()) {
                key = (String)var3.next();
                extrasObject.add(key, new JsonPrimitive((Boolean)this.booleanExtras.get(key)));
            }
        }

        if (null != this.jsonExtras) {
            var3 = this.jsonExtras.keySet().iterator();

            while(var3.hasNext()) {
                key = (String)var3.next();
                extrasObject.add(key, (JsonElement)this.jsonExtras.get(key));
            }
        }

        if (null != this.extras || null != this.numberExtras || null != this.booleanExtras) {
            json.add("extras", extrasObject);
        }

        return json;
    }

    public static class Builder {
        private String title;
        private String msgContent;
        private String contentType;
        private Map<String, String> extrasBuilder;
        private Map<String, Number> numberExtrasBuilder;
        private Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;

        public Builder() {
        }

//        public cn.jpush.api.push.model.Message.Builder setTitle(String title) {
//            this.title = title;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message.Builder setMsgContent(String msgContent) {
//            this.msgContent = msgContent;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message.Builder setContentType(String contentType) {
//            this.contentType = contentType;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message.Builder addExtra(String key, String value) {
//            Preconditions.checkArgument(null != key && null != value, "Key/Value should not be null.");
//            if (null == this.extrasBuilder) {
//                this.extrasBuilder = new HashMap();
//            }
//
//            this.extrasBuilder.put(key, value);
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message.Builder addExtras(Map<String, String> extras) {
//            Preconditions.checkArgument(null != extras, "extras should not be null.");
//            if (null == this.extrasBuilder) {
//                this.extrasBuilder = new HashMap();
//            }
//
//            Iterator var2 = extras.keySet().iterator();
//
//            while(var2.hasNext()) {
//                String key = (String)var2.next();
//                this.extrasBuilder.put(key, extras.get(key));
//            }
//
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message.Builder addExtra(String key, Number value) {
//            Preconditions.checkArgument(null != key && null != value, "Key/Value should not be null.");
//            if (null == this.numberExtrasBuilder) {
//                this.numberExtrasBuilder = new HashMap();
//            }
//
//            this.numberExtrasBuilder.put(key, value);
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message.Builder addExtra(String key, Boolean value) {
//            Preconditions.checkArgument(null != key && null != value, "Key/Value should not be null.");
//            if (null == this.booleanExtrasBuilder) {
//                this.booleanExtrasBuilder = new HashMap();
//            }
//
//            this.booleanExtrasBuilder.put(key, value);
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message.Builder addExtra(String key, JsonObject value) {
//            Preconditions.checkArgument(null != key && null != value, "Key/Value should not be null.");
//            if (null == this.jsonExtrasBuilder) {
//                this.jsonExtrasBuilder = new HashMap();
//            }
//
//            this.jsonExtrasBuilder.put(key, value);
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Message build() {
//            Preconditions.checkArgument(null != this.msgContent, "msgContent should be set");
//            return new cn.jpush.api.push.model.Message(this.title, this.msgContent, this.contentType, this.extrasBuilder, this.numberExtrasBuilder, this.booleanExtrasBuilder, this.jsonExtrasBuilder);
//        }
    }
}
