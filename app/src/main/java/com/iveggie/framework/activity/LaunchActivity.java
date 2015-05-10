package com.iveggie.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.iveggie.framework.utils.StringUtils;
import com.iveggie.iveggie.R;
import com.iveggie.iveggie.activity.LogRegActivity;
import com.iveggie.iveggie.activity.TestActivity;
import com.iveggie.iveggie.fragment.LoginFragment;

/**
 * LaunchActivity
 * Desc: 启动Activity
 * Date: 2015/5/10
 * Time: 15:03
 * Created by: Wooxxx
 */
public class LaunchActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                //判断是否有账号登陆
                if (StringUtils.isEmpty(settingManager.getUser().getToken())) {
                    //未有账号登陆
                    intent.setClass(LaunchActivity.this,
                            LogRegActivity.class);
                } else {
                    //已有账号登陆
                    intent.setClass(LaunchActivity.this,
                            TestActivity.class);
                    intent.putExtra("autoLogin", true);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
