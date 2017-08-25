package com.don.rxjava2test;

import android.os.Bundle;

/**
 * <p>
 * Description：框架Base接口
 * </p>
 * <p>
 * date 2017/3/29
 */

public interface BaseCallback {
    /**
     * 设置布局,必须返回layout的id
     */
    int getContentViewLayoutId();

    /**
     * 设置布局之前的逻辑
     *
     * @param savedInstanceState 被销毁之前保存的数据
     */
    void before(Bundle savedInstanceState);

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();
}
