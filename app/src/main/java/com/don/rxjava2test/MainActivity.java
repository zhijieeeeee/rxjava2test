package com.don.rxjava2test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.don.rxjava2test.http.HttpResult;
import com.don.rxjava2test.http.MySubscriber;
import com.don.rxjava2test.http.RetrofitUtil;
import com.don.rxjava2test.model.CheckAccountModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    Disposable md;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void before(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        Button btn_cancel_sub = (Button) findViewById(R.id.btn_cancel_sub);
        btn_cancel_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                md.dispose();
            }
        });


        //Rxjava2的Observable不在支持背压，Flowable支持

        /**
         *
         Observable/Observer
         Flowable/Subscriber
         Single/SingleObserver
         Completable/CompletableObserver
         Maybe/MaybeObserver
         */

        Observable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        md = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i("MyLog", value + "");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("MyLog", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("MyLog", "onError");
                    }

                });

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("aaa");
                e.onNext("bbb");
                e.onNext("cccc");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.i("MyLog", value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i("MyLog", "onComplete");
            }
        });

        String[] names = new String[]{"1", "2", "3", "4"};
        Flowable.fromArray(names)
                .subscribe(new Subscriber<String>() {
                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        //向上游请求的个数
                        subscription = s;
                        s.request(2);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("MyLog", s);
                        //继续向上游请求
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("MyLog", "Flowable onComplete");
                    }
                });

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("test");
            }
            //指定背压策略
        }, BackpressureStrategy.BUFFER).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("MyLog", s);
            }
        });


        Single.just("sss").subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String value) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

        //Zip操作符(合并，按照最少的来)
        Observable<String> ob1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                e.onNext("B");
                e.onNext("C");
                e.onNext("D");
            }
        }).subscribeOn(Schedulers.io());
        Observable<Integer> ob2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).subscribeOn(Schedulers.io());
        //合并
        Observable.zip(ob1, ob2, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.i("MyLog", value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void initData() {
        requestCheckIsAccount();
    }

    /**
     * 检测账号是否存在
     */
    private void requestCheckIsAccount() {
        final String phone = "15184498571";
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", DesUtil.encode(phone));
        request(RetrofitUtil.create().isAccount(params), new MySubscriber<HttpResult<CheckAccountModel>>(this) {
            @Override
            public void onSuccess(HttpResult<CheckAccountModel> result) {
                Log.i("MyLog", "result.getData().getIs_account()=" + result.getData().getIs_account());
            }

            @Override
            public void onFinal() {
            }
        });
    }
}
