package com.don.rxjava2test;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.don.rxjava2test.http.HttpResult;
import com.don.rxjava2test.http.HttpResultFunc;
import com.don.rxjava2test.http.MySchedulerTransformer;
import com.don.rxjava2test.http.MySubscriber;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


/**
 * <p>
 * Description：基类Activity
 * </p>
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseCallback {

    public CompositeDisposable compositeDisposable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getContentViewLayoutId() != 0) {
            setContentView(getContentViewLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        compositeDisposable = new CompositeDisposable();
        before(savedInstanceState);
        initView();
        initData();
    }


    @Override
    protected void onDestroy() {
        //取消RxJava2的订阅
        compositeDisposable.clear();
        super.onDestroy();
    }

    /**
     * 网络请求
     * 如果data中无数据，直接使用HttpResult<String>
     * 如果data中的实体为User，使用形如HttpResult<User>
     */
    public <T> void request(Observable<HttpResult<T>> observable, MySubscriber<HttpResult<T>> subscriber) {
        observable
                .map(new HttpResultFunc<T>())
                .compose(new MySchedulerTransformer<HttpResult<T>>())
                .subscribeWith(subscriber);
    }

}
