package com.don.rxjava2test.http;

import com.don.rxjava2test.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * <p>
 * Description：自定义的回调类,T 为 HttpResult<?>
 * </p>
 *
 * @date 2017/3/10
 */

public abstract class MySubscriber<T> implements Observer<T> {

    private BaseActivity baseActivity;

    public MySubscriber(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onSubscribe(Disposable d) {
        baseActivity.compositeDisposable.add(d);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        //统一处理异常，弹Toast
        handleHttpException(e);

        onFail(e);
        onFinal();
    }

    @Override
    public void onNext(T t) {

        onSuccess(t);
        onFinal();
    }

    /**
     * 成功回调
     *
     * @param t String或者解析后的实体
     */
    public abstract void onSuccess(T t);

    /**
     * 失败回调，已经默认Toast了网络这些错误和服务器返回的错误
     * 如果还需对服务器返回的status做处理，直接重写这个方法即可
     */
    public void onFail(Throwable e) {
    }

    /**
     * 成功或失败后都会执行这个方法，在这个方法中做关闭加载框，关闭下拉上拉等操作
     */
    public abstract void onFinal();

    /**
     * 处理http异常，打印相关Toast
     */
    public void handleHttpException(Throwable e) {

    }
}
