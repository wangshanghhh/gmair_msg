package gmair.utils;


import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.ApacheHttpClient;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.push.CIDResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class PushClient {
    private IHttpClient _httpClient;
    private String _baseUrl;
    private String _pushPath;
    private String _pushValidatePath;
    private JsonParser _jsonParser;
    private int _apnsProduction;
    private long _timeToLive;

    public PushClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, (HttpProxy)null, ClientConfig.getInstance());
    }

    /** @deprecated */
    @Deprecated
    public PushClient(String masterSecret, String appKey, int maxRetryTimes) {
        this(masterSecret, appKey, maxRetryTimes, (HttpProxy)null);
    }

    /** @deprecated */
    @Deprecated
    public PushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        this._jsonParser = new JsonParser();
        ServiceHelper.checkBasic(appKey, masterSecret);
        ClientConfig conf = ClientConfig.getInstance();
        conf.setMaxRetryTimes(maxRetryTimes);
        this._baseUrl = (String)conf.get("push.host.name");
        this._pushPath = (String)conf.get("push.path");
        this._pushValidatePath = (String)conf.get("push.validate.path");
        this._apnsProduction = (Integer)conf.get("apns.production");
        this._timeToLive = (Long)conf.get("time.to.live");
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }

    public PushClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        this._jsonParser = new JsonParser();
        ServiceHelper.checkBasic(appKey, masterSecret);
        this._baseUrl = (String)conf.get("push.host.name");
        this._pushPath = (String)conf.get("push.path");
        this._pushValidatePath = (String)conf.get("push.validate.path");
        this._apnsProduction = (Integer)conf.get("apns.production");
        this._timeToLive = (Long)conf.get("time.to.live");
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }

    /** @deprecated */
    @Deprecated
    public PushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        this(masterSecret, appKey);
        if (apnsProduction) {
            this._apnsProduction = 1;
        } else {
            this._apnsProduction = 0;
        }

        this._timeToLive = timeToLive;
    }

    /** @deprecated */
    @Deprecated
    public void setDefaults(boolean apnsProduction, long timeToLive) {
        if (apnsProduction) {
            this._apnsProduction = 1;
        } else {
            this._apnsProduction = 0;
        }

        this._timeToLive = timeToLive;
    }

    public void setBaseUrl(String baseUrl) {
        this._baseUrl = baseUrl;
    }

    public PushResult sendPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != pushPayload, "pushPayload should not be null");
        if (this._apnsProduction > 0) {
            pushPayload.resetOptionsApnsProduction(true);
        } else if (this._apnsProduction == 0) {
            pushPayload.resetOptionsApnsProduction(false);
        }

        if (this._timeToLive >= 0L) {
            pushPayload.resetOptionsTimeToLive(this._timeToLive);
        }

        ResponseWrapper response = this._httpClient.sendPost(this._baseUrl + this._pushPath, pushPayload.toString());
        return (PushResult)BaseResult.fromResponse(response, PushResult.class);
    }

    public PushResult sendPushValidate(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != pushPayload, "pushPayload should not be null");
        if (this._apnsProduction > 0) {
            pushPayload.resetOptionsApnsProduction(true);
        } else if (this._apnsProduction == 0) {
            pushPayload.resetOptionsApnsProduction(false);
        }

        if (this._timeToLive >= 0L) {
            pushPayload.resetOptionsTimeToLive(this._timeToLive);
        }

        ResponseWrapper response = this._httpClient.sendPost(this._baseUrl + this._pushValidatePath, pushPayload.toString());
        return (PushResult)BaseResult.fromResponse(response, PushResult.class);
    }

    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(payloadString), "pushPayload should not be empty");

        try {
            this._jsonParser.parse(payloadString);
        } catch (JsonParseException var3) {
            Preconditions.checkArgument(false, "payloadString should be a valid JSON string.");
        }

        ResponseWrapper response = this._httpClient.sendPost(this._baseUrl + this._pushPath, payloadString);
        return (PushResult)BaseResult.fromResponse(response, PushResult.class);
    }

    public PushResult sendPushValidate(String payloadString) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(payloadString), "pushPayload should not be empty");

        try {
            this._jsonParser.parse(payloadString);
        } catch (JsonParseException var3) {
            Preconditions.checkArgument(false, "payloadString should be a valid JSON string.");
        }

        ResponseWrapper response = this._httpClient.sendPost(this._baseUrl + this._pushValidatePath, payloadString);
        return (PushResult)BaseResult.fromResponse(response, PushResult.class);
    }

    public CIDResult getCidList(int count, String type) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(count >= 1 && count <= 1000, "count should not less than 1 or larger than 1000");
        Preconditions.checkArgument(type == null || type.equals("push") || type.equals("schedule"), "type should be \"push\" or \"schedule\"");
        ResponseWrapper responseWrapper;
        if (type != null) {
            responseWrapper = this._httpClient.sendGet(this._baseUrl + this._pushPath + "/cid?count=" + count + "&type=" + type);
        } else {
            responseWrapper = this._httpClient.sendGet(this._baseUrl + this._pushPath + "/cid?count=" + count);
        }

        return (CIDResult)BaseResult.fromResponse(responseWrapper, CIDResult.class);
    }

    public void setHttpClient(IHttpClient client) {
        this._httpClient = client;
    }

    public void close() {
        if (this._httpClient != null && this._httpClient instanceof NettyHttpClient) {
            ((NettyHttpClient)this._httpClient).close();
        } else if (this._httpClient != null && this._httpClient instanceof ApacheHttpClient) {
            ((ApacheHttpClient)this._httpClient).close();
        }

    }
}

