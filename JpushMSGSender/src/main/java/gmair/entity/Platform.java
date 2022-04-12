package gmair.entity;

import cn.jiguang.common.DeviceType;
import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Platform implements PushModel {
    private static final String ALL = "all";
    private final boolean all;
    private final Set<DeviceType> deviceTypes;

    private Platform(boolean all, Set<DeviceType> deviceTypes) {
        this.all = all;
        this.deviceTypes = deviceTypes;
    }

    public static cn.jpush.api.push.model.Platform.Builder newBuilder() {
        return new cn.jpush.api.push.model.Platform.Builder();
    }

    public static cn.jpush.api.push.model.Platform all() {
        return newBuilder().setAll(true).build();
    }

    public static cn.jpush.api.push.model.Platform android() {
        return newBuilder().addDeviceType(DeviceType.Android).build();
    }

    public static cn.jpush.api.push.model.Platform ios() {
        return newBuilder().addDeviceType(DeviceType.IOS).build();
    }

    public static cn.jpush.api.push.model.Platform winphone() {
        return newBuilder().addDeviceType(DeviceType.WinPhone).build();
    }

    public static cn.jpush.api.push.model.Platform android_ios() {
        return newBuilder().addDeviceType(DeviceType.Android).addDeviceType(DeviceType.IOS).build();
    }

    public static cn.jpush.api.push.model.Platform android_winphone() {
        return newBuilder().addDeviceType(DeviceType.Android).addDeviceType(DeviceType.WinPhone).build();
    }

    public static cn.jpush.api.push.model.Platform ios_winphone() {
        return newBuilder().addDeviceType(DeviceType.IOS).addDeviceType(DeviceType.WinPhone).build();
    }

    public boolean isAll() {
        return this.all;
    }

    public JsonElement toJSON() {
        if (this.all) {
            return new JsonPrimitive("all");
        } else {
            JsonArray json = new JsonArray();
            Iterator var2 = this.deviceTypes.iterator();

            while(var2.hasNext()) {
                DeviceType deviceType = (DeviceType)var2.next();
                json.add(new JsonPrimitive(deviceType.value()));
            }

            return json;
        }
    }

    public static class Builder {
        private boolean all;
        private Set<DeviceType> deviceTypes;

        public Builder() {
        }

//        public cn.jpush.api.push.model.Platform.Builder setAll(boolean all) {
//            this.all = all;
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Platform.Builder addDeviceType(DeviceType deviceType) {
//            if (null == this.deviceTypes) {
//                this.deviceTypes = new HashSet();
//            }
//
//            this.deviceTypes.add(deviceType);
//            return this;
//        }
//
//        public cn.jpush.api.push.model.Platform build() {
//            Preconditions.checkArgument(!this.all || null == this.deviceTypes, "Since all is enabled, any platform should not be set.");
//            Preconditions.checkArgument(this.all || null != this.deviceTypes, "No any deviceType is set.");
//            return new cn.jpush.api.push.model.Platform(this.all, this.deviceTypes);
//        }
    }
}
