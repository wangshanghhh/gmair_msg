package gmair.entity;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class PushResult extends BaseResult {
    private static final long serialVersionUID = 93783137655776743L;
    @Expose
    public long msg_id;
    @Expose
    public int sendno;
    @Expose
    public int statusCode;
    @Expose
    public cn.jpush.api.push.PushResult.Error error;

    public PushResult() {
    }

    public class Error {
        @Expose
        String message;
        @Expose
        int code;

        public Error() {
        }

        public String getMessage() {
            return this.message;
        }

        public int getCode() {
            return this.code;
        }
    }
}

