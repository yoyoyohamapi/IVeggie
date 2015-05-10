package com.iveggie.iveggie.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.iveggie.framework.activity.BaseActivity;
import com.iveggie.iveggie.R;
import com.iveggie.iveggie.fragment.LoginFragment;
import com.iveggie.iveggie.fragment.RegisterFragment;

/**
 * LogRegActivity
 * Desc: 登陆注册Activity
 * Date: 2015/5/10
 * Time: 12:40
 * Created by: Wooxxx
 */
public class LogRegActivity extends BaseActivity{
    // 注册及登录出口
    public static final Class entry = TestActivity.class;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);
        Log.d("Woo", "Enter");
        loginFragment = new LoginFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container,loginFragment)
                .commit();
    }

    @Override
    protected void didUserLogin(int error, String errorMessage, String uid, String token) {
        super.didUserLogin(error, errorMessage, uid, token);
        loginFragment.didUserLogin(error, errorMessage, uid, token);
    }

    @Override
    protected void didRequestSendVerifyCode(int error, String errorMessage) {
        super.didRequestSendVerifyCode(error, errorMessage);
        registerFragment.didRequestSendVerifyCode(error, errorMessage);
    }

    @Override
    protected void didRegisterUser(int error, String errorMessage, String uid, String token) {
        super.didRegisterUser(error, errorMessage, uid, token);
        registerFragment.didRegisterUser(error, errorMessage, uid, token);
    }

    public LoginFragment getLoginFragment() {
        return loginFragment;
    }

    public void setLoginFragment(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public RegisterFragment getRegisterFragment() {
        return registerFragment;
    }

    public void setRegisterFragment(RegisterFragment registerFragment) {
        this.registerFragment = registerFragment;
    }
}
