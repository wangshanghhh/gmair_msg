package gmair.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.TimeUnit;
import cn.jiguang.common.Week;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.BooleanResult;
import cn.jiguang.common.resp.DefaultResult;
import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.device.DeviceClient;
import cn.jpush.api.device.OnlineStatus;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.device.TagListResult;
import cn.jpush.api.push.CIDResult;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.MessageStatus;
import cn.jpush.api.report.MessagesResult;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReportClient;
import cn.jpush.api.report.UsersResult;
import cn.jpush.api.report.model.CheckMessagePayload;
import cn.jpush.api.schedule.ScheduleClient;
import cn.jpush.api.schedule.ScheduleListResult;
import cn.jpush.api.schedule.ScheduleMsgIdsResult;
import cn.jpush.api.schedule.ScheduleResult;
import cn.jpush.api.schedule.model.SchedulePayload;
import cn.jpush.api.schedule.model.TriggerPayload;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;

public class JPushClient {
    private final PushClient _pushClient;
    private final ReportClient _reportClient;
    private final DeviceClient _deviceClient;
    private final ScheduleClient _scheduleClient;

    public JPushClient(String masterSecret, String appKey) {
        this._pushClient = new PushClient(masterSecret, appKey);
        this._reportClient = new ReportClient(masterSecret, appKey);
        this._deviceClient = new DeviceClient(masterSecret, appKey);
        this._scheduleClient = new ScheduleClient(masterSecret, appKey);
    }

    public JPushClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        this._pushClient = new PushClient(masterSecret, appKey, proxy, conf);
        this._reportClient = new ReportClient(masterSecret, appKey, proxy, conf);
        this._deviceClient = new DeviceClient(masterSecret, appKey, proxy, conf);
        this._scheduleClient = new ScheduleClient(masterSecret, appKey, proxy, conf);
    }

    /** @deprecated */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes) {
        this._pushClient = new PushClient(masterSecret, appKey, maxRetryTimes);
        this._reportClient = new ReportClient(masterSecret, appKey, maxRetryTimes);
        this._deviceClient = new DeviceClient(masterSecret, appKey, maxRetryTimes);
        this._scheduleClient = new ScheduleClient(masterSecret, appKey, maxRetryTimes);
    }

    /** @deprecated */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        this._pushClient = new PushClient(masterSecret, appKey, maxRetryTimes, proxy);
        this._reportClient = new ReportClient(masterSecret, appKey, maxRetryTimes, proxy);
        this._deviceClient = new DeviceClient(masterSecret, appKey, maxRetryTimes, proxy);
        this._scheduleClient = new ScheduleClient(masterSecret, appKey, maxRetryTimes, proxy);
    }

    /** @deprecated */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy, ClientConfig conf) {
        conf.setMaxRetryTimes(maxRetryTimes);
        this._pushClient = new PushClient(masterSecret, appKey, proxy, conf);
        this._reportClient = new ReportClient(masterSecret, appKey, proxy, conf);
        this._deviceClient = new DeviceClient(masterSecret, appKey, proxy, conf);
        this._scheduleClient = new ScheduleClient(masterSecret, appKey, proxy, conf);
    }

    /** @deprecated */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy, ClientConfig conf, boolean apnsProduction, long timeToLive) {
        conf.setMaxRetryTimes(maxRetryTimes);
        conf.setApnsProduction(apnsProduction);
        conf.setTimeToLive(timeToLive);
        this._pushClient = new PushClient(masterSecret, appKey, proxy, conf);
        this._reportClient = new ReportClient(masterSecret, appKey, proxy, conf);
        this._deviceClient = new DeviceClient(masterSecret, appKey, proxy, conf);
        this._scheduleClient = new ScheduleClient(masterSecret, appKey, proxy, conf);
    }

    /** @deprecated */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        ClientConfig conf = ClientConfig.getInstance();
        conf.setApnsProduction(apnsProduction);
        conf.setTimeToLive(timeToLive);
        this._pushClient = new PushClient(masterSecret, appKey);
        this._reportClient = new ReportClient(masterSecret, appKey);
        this._deviceClient = new DeviceClient(masterSecret, appKey);
        this._scheduleClient = new ScheduleClient(masterSecret, appKey);
    }

    public PushClient getPushClient() {
        return this._pushClient;
    }

    public PushResult sendPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        return this._pushClient.sendPush(pushPayload);
    }

    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        return this._pushClient.sendPush(payloadString);
    }

    public PushResult sendPushValidate(PushPayload payload) throws APIConnectionException, APIRequestException {
        return this._pushClient.sendPushValidate(payload);
    }

    public PushResult sendPushValidate(String payloadString) throws APIConnectionException, APIRequestException {
        return this._pushClient.sendPushValidate(payloadString);
    }

    public CIDResult getCidList(int count, String type) throws APIConnectionException, APIRequestException {
        return this._pushClient.getCidList(count, type);
    }

    public ReceivedsResult getReportReceiveds(String msgIds) throws APIConnectionException, APIRequestException {
        return this._reportClient.getReceiveds(msgIds);
    }

    public UsersResult getReportUsers(TimeUnit timeUnit, String start, int duration) throws APIConnectionException, APIRequestException {
        return this._reportClient.getUsers(timeUnit, start, duration);
    }

    public MessagesResult getReportMessages(String msgIds) throws APIConnectionException, APIRequestException {
        return this._reportClient.getMessages(msgIds);
    }

    public Map<String, MessageStatus> getMessageStatus(CheckMessagePayload payload) throws APIConnectionException, APIRequestException {
        return this._reportClient.getMessagesStatus(payload);
    }

    public PushResult sendNotificationAll(String alert) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.alertAll(alert);
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendNotificationAll(String alert, SMS sms) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.alertAll(alert, sms);
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidNotificationWithAlias(String title, String alert, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias)).setNotification(Notification.android(alert, title, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidNotificationWithAlias(String title, String alert, SMS sms, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias)).setNotification(Notification.android(alert, title, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidNotificationWithRegistrationID(String title, String alert, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.android(alert, title, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidNotificationWithRegistrationID(String title, String alert, SMS sms, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.android(alert, title, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithAlias(String alert, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setNotification(Notification.ios(alert, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithAlias(String alert, SMS sms, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setNotification(Notification.ios(alert, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithAlias(IosAlert alert, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setNotification(Notification.ios(alert, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithAlias(IosAlert alert, SMS sms, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setNotification(Notification.ios(alert, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithAlias(JsonObject alert, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setNotification(Notification.ios(alert, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithAlias(JsonObject alert, SMS sms, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setNotification(Notification.ios(alert, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithRegistrationID(String alert, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.ios(alert, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithRegistrationID(String alert, SMS sms, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.ios(alert, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithRegistrationID(IosAlert alert, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.ios(alert, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithRegistrationID(IosAlert alert, SMS sms, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.ios(alert, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithRegistrationID(JsonObject alert, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.ios(alert, extras)).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithRegistrationID(JsonObject alert, SMS sms, Map<String, String> extras, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.ios(alert, extras)).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendMessageAll(String msgContent) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.messageAll(msgContent);
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendMessageAll(String msgContent, SMS sms) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.messageAll(msgContent, sms);
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidMessageWithAlias(String title, String msgContent, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidMessageWithAlias(String title, String msgContent, SMS sms, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidMessageWithRegistrationID(String title, String msgContent, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(registrationID)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendAndroidMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(registrationID)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosMessageWithAlias(String title, String msgContent, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosMessageWithAlias(String title, String msgContent, SMS sms, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosMessageWithRegistrationID(String title, String msgContent, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendIosMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.registrationId(registrationID)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendMessageWithRegistrationID(String title, String msgContent, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(registrationID)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).build();
        return this._pushClient.sendPush(payload);
    }

    public PushResult sendMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(registrationID)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).setSMS(sms).build();
        return this._pushClient.sendPush(payload);
    }

    public TagAliasResult getDeviceTagAlias(String registrationId) throws APIConnectionException, APIRequestException {
        return this._deviceClient.getDeviceTagAlias(registrationId);
    }

    public DefaultResult updateDeviceTagAlias(String registrationId, boolean clearAlias, boolean clearTag) throws APIConnectionException, APIRequestException {
        return this._deviceClient.updateDeviceTagAlias(registrationId, clearAlias, clearTag);
    }

    public DefaultResult updateDeviceTagAlias(String registrationId, String alias, Set<String> tagsToAdd, Set<String> tagsToRemove) throws APIConnectionException, APIRequestException {
        return this._deviceClient.updateDeviceTagAlias(registrationId, alias, tagsToAdd, tagsToRemove);
    }

    public TagListResult getTagList() throws APIConnectionException, APIRequestException {
        return this._deviceClient.getTagList();
    }

    public BooleanResult isDeviceInTag(String theTag, String registrationID) throws APIConnectionException, APIRequestException {
        return this._deviceClient.isDeviceInTag(theTag, registrationID);
    }

    public DefaultResult addRemoveDevicesFromTag(String theTag, Set<String> toAddUsers, Set<String> toRemoveUsers) throws APIConnectionException, APIRequestException {
        return this._deviceClient.addRemoveDevicesFromTag(theTag, toAddUsers, toRemoveUsers);
    }

    public DefaultResult deleteTag(String theTag, String platform) throws APIConnectionException, APIRequestException {
        return this._deviceClient.deleteTag(theTag, platform);
    }

    public AliasDeviceListResult getAliasDeviceList(String alias, String platform) throws APIConnectionException, APIRequestException {
        return this._deviceClient.getAliasDeviceList(alias, platform);
    }

    public DefaultResult deleteAlias(String alias, String platform) throws APIConnectionException, APIRequestException {
        return this._deviceClient.deleteAlias(alias, platform);
    }

    public Map<String, OnlineStatus> getUserOnlineStatus(String... registrationIds) throws APIConnectionException, APIRequestException {
        return this._deviceClient.getUserOnlineStatus(registrationIds);
    }

    public DefaultResult bindMobile(String registrationId, String mobile) throws APIConnectionException, APIRequestException {
        return this._deviceClient.bindMobile(registrationId, mobile);
    }

    public ScheduleResult createSingleSchedule(String name, String time, PushPayload push) throws APIConnectionException, APIRequestException {
        TriggerPayload trigger = TriggerPayload.newBuilder().setSingleTime(time).buildSingle();
        SchedulePayload payload = SchedulePayload.newBuilder().setName(name).setEnabled(true).setTrigger(trigger).setPush(push).build();
        return this._scheduleClient.createSchedule(payload);
    }

    public ScheduleResult createDailySchedule(String name, String start, String end, String time, PushPayload push) throws APIConnectionException, APIRequestException {
        return this.createPeriodicalSchedule(name, start, end, time, TimeUnit.DAY, 1, (String[])null, push);
    }

    public ScheduleResult createDailySchedule(String name, String start, String end, String time, int frequency, PushPayload push) throws APIConnectionException, APIRequestException {
        return this.createPeriodicalSchedule(name, start, end, time, TimeUnit.DAY, frequency, (String[])null, push);
    }

    public ScheduleResult createWeeklySchedule(String name, String start, String end, String time, Week[] days, PushPayload push) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != days && days.length > 0, "The days must not be empty.");
        String[] points = new String[days.length];

        for(int i = 0; i < days.length; ++i) {
            points[i] = days[i].name();
        }

        return this.createPeriodicalSchedule(name, start, end, time, TimeUnit.WEEK, 1, points, push);
    }

    public ScheduleResult createWeeklySchedule(String name, String start, String end, String time, int frequency, Week[] days, PushPayload push) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != days && days.length > 0, "The days must not be empty.");
        String[] points = new String[days.length];

        for(int i = 0; i < days.length; ++i) {
            points[i] = days[i].name();
        }

        return this.createPeriodicalSchedule(name, start, end, time, TimeUnit.WEEK, frequency, points, push);
    }

    public ScheduleResult createMonthlySchedule(String name, String start, String end, String time, String[] points, PushPayload push) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != points && points.length > 0, "The points must not be empty.");
        return this.createPeriodicalSchedule(name, start, end, time, TimeUnit.MONTH, 1, points, push);
    }

    public ScheduleResult createMonthlySchedule(String name, String start, String end, String time, int frequency, String[] points, PushPayload push) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != points && points.length > 0, "The points must not be empty.");
        return this.createPeriodicalSchedule(name, start, end, time, TimeUnit.MONTH, frequency, points, push);
    }

    public ScheduleResult getSchedule(String scheduleId) throws APIConnectionException, APIRequestException {
        return this._scheduleClient.getSchedule(scheduleId);
    }

    public ScheduleMsgIdsResult getScheduleMsgIds(String scheduleId) throws APIConnectionException, APIRequestException {
        return this._scheduleClient.getScheduleMsgIds(scheduleId);
    }

    public ScheduleListResult getScheduleList() throws APIConnectionException, APIRequestException {
        return this._scheduleClient.getScheduleList(1);
    }

    public ScheduleListResult getScheduleList(int page) throws APIConnectionException, APIRequestException {
        return this._scheduleClient.getScheduleList(page);
    }

    public ScheduleResult updateScheduleName(String scheduleId, String name) throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder().setName(name).build();
        return this.updateSchedule(scheduleId, payload);
    }

    public ScheduleResult enableSchedule(String scheduleId) throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder().setEnabled(true).build();
        return this.updateSchedule(scheduleId, payload);
    }

    public ScheduleResult disableSchedule(String scheduleId) throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder().setEnabled(false).build();
        return this.updateSchedule(scheduleId, payload);
    }

    public ScheduleResult updateScheduleTrigger(String scheduleId, TriggerPayload trigger) throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder().setTrigger(trigger).build();
        return this.updateSchedule(scheduleId, payload);
    }

    public ScheduleResult updateSchedulePush(String scheduleId, PushPayload push) throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder().setPush(push).build();
        return this.updateSchedule(scheduleId, payload);
    }

    public ScheduleResult updateSchedule(String scheduleId, SchedulePayload payload) throws APIConnectionException, APIRequestException {
        return this._scheduleClient.updateSchedule(scheduleId, payload);
    }

    public void deleteSchedule(String scheduleId) throws APIConnectionException, APIRequestException {
        this._scheduleClient.deleteSchedule(scheduleId);
    }

    private ScheduleResult createPeriodicalSchedule(String name, String start, String end, String time, TimeUnit timeUnit, int frequency, String[] point, PushPayload push) throws APIConnectionException, APIRequestException {
        TriggerPayload trigger = TriggerPayload.newBuilder().setPeriodTime(start, end, time).setTimeFrequency(timeUnit, frequency, point).buildPeriodical();
        SchedulePayload payload = SchedulePayload.newBuilder().setName(name).setEnabled(true).setTrigger(trigger).setPush(push).build();
        return this._scheduleClient.createSchedule(payload);
    }

    public void close() {
        this._pushClient.close();
    }
}
