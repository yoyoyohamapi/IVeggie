package com.iveggie.iveggie.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iveggie.framework.activity.BaseActivity;
import com.iveggie.framework.entity.User;
import com.iveggie.framework.fragment.BackHandleFragment;
import com.iveggie.framework.sdk.SettingManager;
import com.iveggie.iveggie.R;
import com.iveggie.iveggie.activity.LogRegActivity;

/**
 * LoginFragment
 * Desc: 登录Fragment
 * Date: 2015/5/9
 * Time: 21:54
 * Created by: Wooxxx
 */
public class LoginFragment extends BackHandleFragment {

    private EditText phoneEditor;
    private EditText pwdEditor;
    private Button submit;
    private TextView toRegTxt;
    private TextView forgetPwdTxt;

    private Resources rs;
    String phone,pwd;

    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum HANDLER_KEY {

        /** 登陆成功. */
        LOGIN_SUCCESS,

        /** 登陆失败. */
        LOGIN_FAIL,

        /** 登录超时. */
        LOGIN_TIMEOUT,

    }

    /**
     * The handler.
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HANDLER_KEY key = HANDLER_KEY.values()[msg.what];
            switch (key) {
                // 登陆成功
                case LOGIN_SUCCESS:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LogRegActivity.entry);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                // 登陆失败
                case LOGIN_FAIL:
                    phoneEditor.setError(rs.getString(R.string.login_error));
                    phoneEditor.requestFocus();
                    break;
                // 登录超时
                case LOGIN_TIMEOUT:
                    Toast.makeText(getActivity(), "登陆超时", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void initViews() {
        phoneEditor = (EditText) rootView.findViewById(R.id.phone);
        pwdEditor = (EditText) rootView.findViewById(R.id.password);
        submit = (Button)rootView.findViewById(R.id.submit);
        toRegTxt = (TextView)rootView.findViewById(R.id.to_register);
        forgetPwdTxt = (TextView)rootView.findViewById(R.id.forget_pwd);
        rs = getResources();
    }

    @Override
    public void setListeners() {
        // 提交按钮按下，进行登录验证
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获得手机号
                phone = phoneEditor.getText().toString();
                // 获得密码
                pwd = pwdEditor.getText().toString();
                ((BaseActivity)getActivity()).getmCenter()
                        .cLogin(phone,pwd);
            }
        });

        // 跳转到注册页面
        toRegTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_container, registerFragment);
                ((LogRegActivity)getActivity()).setRegisterFragment(registerFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        // 跳转到忘记密码页面
        forgetPwdTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public  void didUserLogin(int error, String errorMessage, String uid,
                                String token) {
        Message msg = new Message();
        if (!uid.isEmpty() && !token.isEmpty()) {// 登陆成功
            SettingManager settingManager =( (BaseActivity)getActivity()).getSettingManager();
            User user = new User(
                    "",
                    phone,
                    pwd,
                    token,
                    uid
            );
            settingManager.setUser(user);
            msg.what = HANDLER_KEY.LOGIN_SUCCESS.ordinal();
        } else {// 登陆失败
            msg.what = HANDLER_KEY.LOGIN_FAIL.ordinal();
            msg.obj = errorMessage;
        }
        handler.sendMessage(msg);

    }
    @Override
    public boolean onBackPressed() {
        return true;
    }
}
