package com.iveggie.iveggie.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iveggie.framework.activity.BaseActivity;
import com.iveggie.framework.entity.User;
import com.iveggie.framework.fragment.BackHandleFragment;
import com.iveggie.framework.sdk.CmdCenter;
import com.iveggie.framework.sdk.SettingManager;
import com.iveggie.framework.utils.Validator;
import com.iveggie.iveggie.R;
import com.xtremeprog.xpgconnect.XPGWifiErrorCode;
import com.xtremeprog.xpgconnect.XPGWifiSDK;
import com.xtremeprog.xpgconnect.XPGWifiSDKListener;

/**
 * RegisterFragment
 * Desc: 注册Fragment
 * Date: 2015/5/9
 * Time: 21:54
 * Created by: Wooxxx
 */
public class RegisterFragment extends BackHandleFragment {

    private EditText phoneEditor;
    private EditText pwdEditor;
    private EditText rePwdEditor;
    private EditText authCodeEditor;
    private Button getAuthCode;
    private Button backToLogin;
    private Button submit;

    private Resources rs;

    private String phone;
    private String password;

    private TimeCount timer;

    private CmdCenter cmdCenter;

    // 定义注册表单验证错误代码
    private final int NO_ERROR = 0; //无错误
    private final int PHONE_ERROR = 1; // 手机号码格式错误
    private final int PASSWORD_ERROR = 2; // 密码格式错误
    private final int PASSWORD_MISMATCH = 3; // 两次输入密码不一致

    /**
     * Handler消息
     */
    private enum HANDLER_KEY {
        /**
         * 请求验证码成功
         */
        REQUEST_AUTH_CODE_SUCCESS,

        /**
         * 请求验证码失败
         */
        REQUEST_AUTH_CODE_FAIL,

        /**
         * 注册成功
         */
        REG_SUCCESS,

        /**
         * 注册失败
         */
        REG_FAIL,
    };

    /**
     * 异步线程，防止UI阻塞
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HANDLER_KEY key = HANDLER_KEY.values()[msg.what];
            switch (key){
                case REQUEST_AUTH_CODE_SUCCESS:
                    timer = new TimeCount(60000, 1000);
                    timer.start();
                    break;

                case REQUEST_AUTH_CODE_FAIL:
                    Toast.makeText(
                            RegisterFragment.this.getActivity(),
                            rs.getString(R.string.register_ac_request_error),
                            Toast.LENGTH_SHORT).show();
                    break;

                case REG_SUCCESS:
                    Toast.makeText(
                            RegisterFragment.this.getActivity(),
                           "注册成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case REG_FAIL:
                    phoneEditor.setError(rs.getString(R.string.register_phone_exist));
                    phoneEditor.requestFocus();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void initViews() {
       phoneEditor = (EditText) rootView.findViewById(R.id.phone);
        pwdEditor = (EditText)rootView.findViewById(R.id.password);
        rePwdEditor = (EditText)rootView.findViewById(R.id.re_password);
        authCodeEditor = (EditText)rootView.findViewById(R.id.auth_code);
        getAuthCode = (Button) rootView.findViewById(R.id.get_auth_code);
        backToLogin = (Button) rootView.findViewById(R.id.back);
        submit = (Button) rootView.findViewById(R.id.submit);
        rs = getResources();
        cmdCenter = ((BaseActivity)getActivity()).getmCenter();
    }

    @Override
    public void setListeners() {
        // 设置机智云的监听
        XPGWifiSDK.sharedInstance().setListener(new XPGWifiSDKListener(){
            @Override
            public void didRequestSendVerifyCode(int error, String errorMessage) {
                Message msg = new Message();
                if(error== XPGWifiErrorCode.XPGWifiError_NONE){
                    //请求成功，等待包含验证码的手机短信,
                    msg.what = HANDLER_KEY.REQUEST_AUTH_CODE_SUCCESS.ordinal();
                }else{
                    //请求失败，弹出错误信息
                    msg.what = HANDLER_KEY.REQUEST_AUTH_CODE_FAIL.ordinal();
                }
                handler.sendMessage(msg);

            }

            @Override
            public void didRegisterUser(int error, String errorMessage, String uid, String token) {

            }
        });
        // 获得验证码
        getAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int errorCode = validateForm();
                switch (errorCode) {
                    case NO_ERROR:
                        //开始验证码倒计，并请求验证码
                        XPGWifiSDK.sharedInstance().requestSendVerifyCode(phone);
                        break;
                    case PHONE_ERROR:
                        phoneEditor.setError(rs.getString(R.string.register_phone_error));
                        phoneEditor.requestFocus();
                        break;
                    case PASSWORD_ERROR:
                        pwdEditor.setError(rs.getString(R.string.register_pwd_error));
                        pwdEditor.requestFocus();
                        break;
                    case PASSWORD_MISMATCH:
                        pwdEditor.setError(rs.getString(R.string.register_pwd_mismatch));
                        pwdEditor.requestFocus();
                        break;
                }
            }
        });

        // 返回至登陆页
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment.this.onBackPressed();
            }
        });

        // 提交注册
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提交注册时判断验证码是否正确
                String authCode = authCodeEditor.getText()
                        .toString();
                if (authCode.length() == 6) {
                    cmdCenter.cRegisterPhoneUser(phone, authCode, password);
                } else {
                    authCodeEditor.setError(rs.getString(R.string.register_ac_error));
                    authCodeEditor.requestFocus();
                }
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();
        return true;
    }

    /**
     * 验证码倒计时类
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 开始计时时，按钮文字
            getAuthCode.setClickable(false);
            getAuthCode.setText(millisUntilFinished / 1000 + rs
                    .getString(R.string.register_ac_wait));
        }

        @Override
        public void onFinish() {
            getAuthCode.setText(rs.getString(R.string.register_ac_reget));
            getAuthCode.setClickable(true);
        }
    }

    /**
     * 验证注册表单
     *
     * @return 返回错误代码
     */
    private int validateForm() {
        // 验证手机
        phone = phoneEditor.getText()
                .toString();
        if (!Validator.validatePhone(phone)) {
            return PHONE_ERROR;
        }

        // 验证密码
        password = pwdEditor.getText()
                .toString();
        String rePassword = rePwdEditor.getText()
                .toString();
        Log.d("PwdTest",password);
        Log.d("PwdTest",rePassword);
        if (!password.equals(rePassword))
            return PASSWORD_MISMATCH;
        else {
            if (!Validator.validatePassword(password))
                return PASSWORD_ERROR;
        }

        return NO_ERROR;
    }

    /*
	 * 用户注册结果回调接口.
	 */
    public void didRegisterUser(int error, String errorMessage, String uid,
                                   String token) {
        Message msg = new Message();
        if(error== XPGWifiErrorCode.XPGWifiError_NONE){
            //注册成功，获取到uid和token
            msg.what = HANDLER_KEY.REG_SUCCESS.ordinal();
            msg.obj = "注册成功";
            SettingManager settingManager = ((BaseActivity)getActivity()).getSettingManager();
            User  user = new User(
                    "",
                    phone,
                    password,
                    token,
                    uid
            );
            settingManager.setUser(user);
        }else {
            msg.what = HANDLER_KEY.REG_SUCCESS.ordinal();
        }
        handler.sendMessage(msg);
    }


    public void didRequestSendVerifyCode(int error, String errorMessage) {
        Message msg = new Message();
        if(error== XPGWifiErrorCode.XPGWifiError_NONE){
            //请求成功，等待包含验证码的手机短信,
            msg.what = HANDLER_KEY.REQUEST_AUTH_CODE_SUCCESS.ordinal();
        }else{
            //请求失败，弹出错误信息
            msg.what = HANDLER_KEY.REQUEST_AUTH_CODE_FAIL.ordinal();
        }
        handler.sendMessage(msg);
    }

}
