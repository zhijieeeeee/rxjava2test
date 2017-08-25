package com.don.rxjava2test.http;


import com.don.rxjava2test.common.HttpConstant;
import com.don.rxjava2test.http.api.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>
 * Description：Retrofit工具类
 * </p>
 */
public class RetrofitUtil {

    /**
     * 超时时间（单位s）
     */
    private static final int DEFAULT_TIMEOUT = 60;

    public static <T> T create(Class<T> service) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(HttpConstant.BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .callFactory(RetrofitUtil.genericClient());
        Retrofit mRetrofit = builder.build();
        return mRetrofit.create(service);
    }

    public static ApiService create() {
        return create(ApiService.class);
    }

    /**
     * 配置OkHttpClient
     */
    public static OkHttpClient genericClient() {
        //用于打印http信息的拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .addInterceptor(interceptor)
//                .addNetworkInterceptor(headerInterceptor)
//                .addInterceptor(receivedCookiesInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

//    /**
//     * 添加公共请求头
//     *
//     * @return
//     */
//    static Interceptor headerInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request()
//                    .newBuilder()
////                    .header("Connection","close")
//                    .header("Cookie", SPUtil.getString(SPConstant.COOKIE))
//                    .build();
//            return chain.proceed(request);
//        }
//    };

//    static Interceptor receivedCookiesInterceptor = new Interceptor() {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            Response originalResponse = chain.proceed(chain.request());
//            //这里获取请求返回的cookie
//            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//                final StringBuffer cookieBuffer = new StringBuffer();
//                final StringBuffer cookieForH5Buffer = new StringBuffer();
//                Observable.from(originalResponse.headers("Set-Cookie"))
//                        .map(new Func1<String, String>() {
//                            @Override
//                            public String call(String s) {
//                                //用于WebView同步Cookie使用搞得
////                                MyApplication.cookieList.add(s);
//                                cookieForH5Buffer.append(s).append("!!");
//                                return s;
//                            }
//                        })
//                        .subscribe(new Action1<String>() {
//                            @Override
//                            public void call(String cookie) {
//                                cookieBuffer.append(cookie).append(";");
//                            }
//                        });
//                //用于http请求设置cookie使用
//                SPUtil.saveString(SPConstant.COOKIE, cookieBuffer.toString());
//                SPUtil.saveString(SPConstant.COOKIE_FOR_H5, cookieForH5Buffer.toString());
//            }
//
//            return originalResponse;
//        }
//    };
}
