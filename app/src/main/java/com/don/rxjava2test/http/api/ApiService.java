package com.don.rxjava2test.http.api;


import com.don.rxjava2test.common.HttpConstant;
import com.don.rxjava2test.http.HttpResult;
import com.don.rxjava2test.model.CheckAccountModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * <p>
 * Description：接口API，必须为Observable<HttpResult<T>>类型
 * T 根据data中返回的数据定义实体，如Observable<HttpResult<LoginModel>>
 * 如果data为空，直接使用Observable<HttpResult<String>>
 * </p>
 */

public interface ApiService {

    //----------------------通行证------------------//

    //检查是否为账号
    @FormUrlEncoded
    @POST(HttpConstant.IS_ACCOUNT)
    Observable<HttpResult<CheckAccountModel>> isAccount(@FieldMap Map<String, String> param);

}
