package com.don.rxjava2test.http;

/**
 * <p>
 * Description：为了处理统一格式的json字符串
 * </p>
 *
 */
public class HttpResult<T> {

    //成功时返回status:0和data:{}或者data:[] { “status”: 0, “data”: { } } { “status”: 0, “data”: [ ] }
    //失败时返回status:(错误码代号)和info:(错误原因) { “status”: 1, “info”: “帐号或密码错误” }
    private int status;

    private String info;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
