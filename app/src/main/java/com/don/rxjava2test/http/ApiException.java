package com.don.rxjava2test.http;

/**
 * <p>
 * Description：携带json中错误信息的Exception
 * </p>
 */
public class ApiException extends RuntimeException {

    //错误对应的状态码
    public int status;
    public String msg;

    public ApiException(String msg, int status) {
        super(msg);
        this.status = status;
        this.msg=msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
