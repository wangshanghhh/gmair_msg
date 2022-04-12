package gmair.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 极光推送客户端
 *
 */
@Component
public class MyJPushClient {
    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.masterSecret}")
    private String masterSecret;

    @Value("${jpush.apnsProduction}")
    private boolean apnsProduction;

    private static JPushClient jPushClient = null;

    private static final int RESPONSE_OK = 200;

    private static final Logger logger = LoggerFactory.getLogger(MyJPushClient.class);

    public String getAppKey() {
        return appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public boolean isApnsProduction() {
        return apnsProduction;
    }

    public synchronized JPushClient getJPushClient() {
        if (jPushClient == null) {
            jPushClient = new JPushClient(masterSecret, appKey);
        }

        return jPushClient;
    }

    public static int getResponseOk() {
        return RESPONSE_OK;
    }

    public static Logger getLogger() {
        return logger;
    }
}
