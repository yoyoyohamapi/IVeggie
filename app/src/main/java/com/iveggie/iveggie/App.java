package com.iveggie.iveggie;

import android.app.Application;

import com.iveggie.framework.helper.GoKitHelper;

/**
 * App
 * Desc: 应用环境上下文
 * Date: 2015/5/9
 * Time: 19:32
 * Created by: Wooxxx
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GoKitHelper.initGokit(getApplicationContext());
    }
}
