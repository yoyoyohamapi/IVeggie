package com.iveggie.framework.sdk;

import android.content.Context;
import android.util.Log;

import com.iveggie.framework.config.Configs;
import com.iveggie.framework.config.JsonKeys;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiSDK;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * CmdCenter
 * Desc: 指令中心
 * Date: 2015/5/10
 * Time: 12:23
 * Created by: Wooxxx
 */
public class CmdCenter {
    /** The Constant TAG. */
    private static final String TAG = "CmdCenter";

    /**
     * The xpg wifi sdk.
     */
    private static XPGWifiSDK xpgWifiGCC;

    /**
     * The m center.
     */
    private static CmdCenter mCenter;

    /**
     * The m setting manager.
     */
    private SettingManager mSettingManager;

    /**
     * Instantiates a new cmd center.
     *
     * @param c
     *            the c
     */
    private CmdCenter(Context c) {
        if (mCenter == null) {
            init(c);
        }
    }

    /**
     * Gets the single instance of CmdCenter.
     *
     * @param c
     *            the c
     * @return single instance of CmdCenter
     */
    public static CmdCenter getInstance(Context c) {
        if (mCenter == null) {
            mCenter = new CmdCenter(c);
        }
        return mCenter;
    }

    /**
     * Inits the.
     *
     * @param c
     *            上下文环境
     */
    private void init(Context c) {
        mSettingManager = new SettingManager(c);
        xpgWifiGCC = XPGWifiSDK.sharedInstance();
    }

    /**
     * Gets the XPG wifi sdk.
     *
     * @return the XPG wifi sdk
     */
    public XPGWifiSDK getXPGWifiSDK() {
        return xpgWifiGCC;
    }

    // =================================================================
    //
    // 关于账号的指令
    //
    // =================================================================

    /**
     * 注册账号.
     *
     * @param phone
     *            注册手机号
     * @param code
     *            验证码
     * @param password
     *            注册密码
     */
    public void cRegisterPhoneUser(String phone, String code, String password) {
        xpgWifiGCC.registerUserByPhoneAndCode(phone, password, code);
    }


    /**
     * 匿名登录
     * <p/>
     * 如果一开始不需要直接注册账号，则需要进行匿名登录.
     */
    public void cLoginAnonymousUser() {
        xpgWifiGCC.userLoginAnonymous();
    }

    /**
     * 账号注销.
     */
    public void cLogout() {
        Log.e(TAG, "cLogout:uesrid=" + mSettingManager.getUser().getUid());
        xpgWifiGCC.userLogout(mSettingManager.getUser().getUid());
        mSettingManager.clean();
    }

    /**
     * 账号登陆.
     *
     * @param name
     *            用户名
     * @param psw
     *            密码
     */
    public void cLogin(String name, String psw) {
        xpgWifiGCC.userLoginWithUserName(name, psw);
    }

    /**
     * 忘记密码.
     *
     * @param phone
     *            手机号
     * @param code
     *            验证码
     * @param newPassword
     *            the new password
     */
    public void cChangeUserPasswordWithCode(String phone, String code,
                                            String newPassword) {
        xpgWifiGCC.changeUserPasswordByCode(phone, code, newPassword);
    }

    /**
     * 修改密码.
     *
     * @param token
     *            令牌
     * @param oldPsw
     *            旧密码
     * @param newPsw
     *            新密码
     */
    public void cChangeUserPassword(String token, String oldPsw, String newPsw) {
        xpgWifiGCC.changeUserPassword(token, oldPsw, newPsw);
    }

    /**
     * 根据邮箱修改密码.
     *
     * @param email            邮箱地址
     */
    public void cChangePassworfByEmail(String email) {
        xpgWifiGCC.changeUserPasswordByEmail(email);
    }

    /**
     * 请求向手机发送验证码.
     *
     * @param phone
     *            手机号
     */
    public void cRequestSendVerifyCode(String phone) {
        xpgWifiGCC.requestSendVerifyCode(phone);
    }

    /**
     * 发送airlink广播，把需要连接的wifi的ssid和password发给模块。.
     *
     * @param wifi
     *            wifi名字
     * @param password
     *            wifi密码
     */
    public void cSetAirLink(String wifi, String password) {
        xpgWifiGCC.setDeviceWifi(wifi, password,
                XPGWifiSDK.XPGWifiConfigureMode.XPGWifiConfigureModeAirLink, 60);
    }

    /**
     * softap，把需要连接的wifi的ssid和password发给模块。.
     *
     * @param wifi
     *            wifi名字
     * @param password
     *            wifi密码
     */
    public void cSetSoftAp(String wifi, String password) {
        xpgWifiGCC.setDeviceWifi(wifi, password,
                XPGWifiSDK.XPGWifiConfigureMode.XPGWifiConfigureModeSoftAP, 30);
    }

    /**
     * 绑定后刷新设备列表，该方法会同时获取本地设备以及远程设备列表.
     *
     * @param uid
     *            用户名
     * @param token
     *            令牌
     */
    public void cGetBoundDevices(String uid, String token) {
        xpgWifiGCC.getBoundDevices(uid, token, Configs.PRODUCT_KEY);
        // xpgWifiSdk.getBoundDevices(uid, token);
    }

    /**
     * 绑定设备.
     *
     * @param uid
     *            用户名
     * @param token
     *            密码
     * @param did
     *            did
     * @param passcode
     *            passcode
     * @param remark
     *            备注
     */
    public void cBindDevice(String uid, String token, String did,
                            String passcode, String remark) {

        xpgWifiGCC.bindDevice(uid, token, did, passcode, remark);
    }

    // =================================================================
    //
    // 关于控制设备的指令
    //
    // =================================================================

    /**
     * 发送指令.
     *
     * @param xpgWifiDevice            the xpg wifi device
     * @param key the key
     * @param value the value
     */
    public void cWrite(XPGWifiDevice xpgWifiDevice, String key, Object value) {

        try {
            final JSONObject jsonsend = new JSONObject();
            JSONObject jsonparam = new JSONObject();
            jsonsend.put("cmd", 1);
            jsonparam.put(key, value);
            jsonsend.put(JsonKeys.KEY_ACTION, jsonparam);
            Log.i("sendjson", jsonsend.toString());
            xpgWifiDevice.write(jsonsend.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取设备状态.
     *
     * @param xpgWifiDevice            the xpg wifi device
     */
    public void cGetStatus(XPGWifiDevice xpgWifiDevice) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        xpgWifiDevice.write(json.toString());
    }

    /**
     * 断开连接.
     *
     * @param xpgWifiDevice
     *            the xpg wifi device
     */
    public void cDisconnect(XPGWifiDevice xpgWifiDevice) {
        xpgWifiDevice.disconnect();
    }

    /**
     * 解除绑定.
     *
     * @param uid
     *            the uid
     * @param token
     *            the token
     * @param did
     *            the did
     * @param passCode
     *            the pass code
     */
    public void cUnbindDevice(String uid, String token, String did,
                              String passCode) {
        xpgWifiGCC.unbindDevice(uid, token, did, passCode);
    }

    /**
     * 更新备注.
     *
     * @param uid
     *            the uid
     * @param token
     *            the token
     * @param did
     *            the did
     * @param passCode
     *            the pass code
     * @param remark
     *            the remark
     */
    public void cUpdateRemark(String uid, String token, String did,
                              String passCode, String remark) {
        xpgWifiGCC.bindDevice(uid, token, did, passCode, remark);
    }
}
