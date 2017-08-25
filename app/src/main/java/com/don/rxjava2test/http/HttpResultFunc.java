package com.don.rxjava2test.http;

import io.reactivex.functions.Function;

/**
 * <p>
 * Description： 统一处理Http的resultCode
 * </p>
 */
public class HttpResultFunc<T> implements Function<HttpResult<T>, HttpResult<T>> {

    @Override
    public HttpResult<T> apply(HttpResult<T> httpResult) throws Exception {
        if (httpResult.getStatus() != 0) {//失败
            throw new ApiException(httpResult.getInfo(), httpResult.getStatus());
        }
        return httpResult;
    }
}
