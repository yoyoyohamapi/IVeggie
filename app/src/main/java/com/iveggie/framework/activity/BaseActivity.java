package com.iveggie.framework.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.iveggie.framework.fragment.BackHandleFragment;
import com.iveggie.framework.sdk.CmdCenter;
import com.iveggie.framework.sdk.SettingManager;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiDeviceListener;
import com.xtremeprog.xpgconnect.XPGWifiSDKListener;
import com.xtremeprog.xpgconnect.XPGWifiSSID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BaseActivity
 * Desc: Activity基类,该基类实现了XPGWifiDeviceListener和XPGWifiSDKListener两个监听器，并提供全局的回调方法
 * Date: 2015/5/9
 * Time: 21:53
 * Created by: Wooxxx
 */
public class BaseActivity extends FragmentActivity
        implements BackHandleFragment.BackHandledInterface {

    /**
     * 设备列表
     */
    protected static List<XPGWifiDevice> devicesList = new ArrayList<XPGWifiDevice>();

    /**
     * 绑定的设备列表
     */
    protected static List<XPGWifiDevice> bindList = new ArrayList<XPGWifiDevice>();

    /**
     * SharedPreference 处理类
     */
    protected SettingManager settingManager;

    /**
     * 控制指令
     */
    protected CmdCenter mCenter;

    private Handler handler = new Handler();

    /**
     * XPGWifiDeviceListener
     * <p/>
     * 设备属性监听器。 设备连接断开、获取绑定参数、获取设备信息、控制和接受设备信息相关.
     */
    protected XPGWifiDeviceListener deviceListener = new XPGWifiDeviceListener() {

        @Override
        public void didDeviceOnline(XPGWifiDevice device, boolean isOnline) {
            BaseActivity.this.didDeviceOnline(device, isOnline);
        }

        @Override
        public void didDisconnected(XPGWifiDevice device) {
            BaseActivity.this.didDisconnected(device);
        }

        @Override
        public void didLogin(XPGWifiDevice device, int result) {
            BaseActivity.this.didLogin(device, result);
        }

        @Override
        public void didReceiveData(XPGWifiDevice device,
                                   ConcurrentHashMap<String, Object> dataMap, int result) {
            BaseActivity.this.didReceiveData(device, dataMap, result);
        }

    };

    /**
     * XPGWifiSDKListener
     * <p/>
     * sdk监听器。 配置设备上线、注册登录用户、搜索发现设备、用户绑定和解绑设备相关.
     */
    private XPGWifiSDKListener sdkListener = new XPGWifiSDKListener() {

        @Override
        public void didBindDevice(int error, String errorMessage, String did) {
            BaseActivity.this.didBindDevice(error, errorMessage, did);
        }

        @Override
        public void didChangeUserEmail(int error, String errorMessage) {
            BaseActivity.this.didChangeUserEmail(error, errorMessage);
        }

        @Override
        public void didChangeUserPassword(int error, String errorMessage) {
            BaseActivity.this.didChangeUserPassword(error, errorMessage);
        }

        @Override
        public void didChangeUserPhone(int error, String errorMessage) {
            BaseActivity.this.didChangeUserPhone(error, errorMessage);
        }

        @Override
        public void didDiscovered(int error, List<XPGWifiDevice> devicesList) {

            BaseActivity.this.didDiscovered(error, devicesList);
        }

        @Override
        public void didGetSSIDList(int error, List<XPGWifiSSID> ssidInfoList) {
            BaseActivity.this.didGetSSIDList(error, ssidInfoList);
        }

        @Override
        public void didRegisterUser(int error, String errorMessage, String uid,
                                    String token) {
            BaseActivity.this.didRegisterUser(error, errorMessage, uid, token);
        }

        @Override
        public void didRequestSendVerifyCode(int error, String errorMessage) {
            BaseActivity.this.didRequestSendVerifyCode(error, errorMessage);
        }

        @Override
        public void didSetDeviceWifi(int error, XPGWifiDevice device) {
            BaseActivity.this.didSetDeviceWifi(error, device);
        }

        @Override
        public void didUnbindDevice(int error, String errorMessage, String did) {
            BaseActivity.this.didUnbindDevice(error, errorMessage, did);
        }

        @Override
        public void didUserLogin(int error, String errorMessage, String uid,
                                 String token) {
            BaseActivity.this.didUserLogin(error, errorMessage, uid, token);
        }

        @Override
        public void didUserLogout(int error, String errorMessage) {
            BaseActivity.this.didUserLogout(error, errorMessage);
        }

    };

    //------------------------------------
    //-----------返回键监听相关
    //------------------------------------
    // 监听返回键的Fragment对象
    protected BackHandleFragment backHandledFragment;

    @Override
    public void setSelectedFragment(BackHandleFragment selectedFragment) {
        this.backHandledFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        /*
        *如果不存在BackHandledFragment对象或者该对象重写的onBackPressed方法
        则执行默认的返回键逻辑
         */
        if (backHandledFragment == null || !backHandledFragment.onBackPressed()) {
            if (getFragmentManager().getBackStackEntryCount() == 0)
                super.onBackPressed();
            else
                getSupportFragmentManager().popBackStack();
        }
    }


    /**
     * 用户登出回调借口.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     */
    protected void didUserLogout(int error, String errorMessage) {

    }

    /**
     * 用户登陆回调接口.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     * @param uid
     *            用户id
     * @param token
     *            授权令牌
     */
    protected void didUserLogin(int error, String errorMessage, String uid,
                                String token) {

    }

    /**
     * 设备解除绑定回调接口.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     * @param did
     *            设备注册id
     */
    protected void didUnbindDevice(int error, String errorMessage, String did) {

    }

    /**
     * 设备配置结果回调.
     *
     * @param error
     *            结果代码
     * @param device
     *            设备对象
     */
    protected void didSetDeviceWifi(int error, XPGWifiDevice device) {

    }

    /**
     * 请求手机验证码回调接口.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     */
    protected void didRequestSendVerifyCode(int error, String errorMessage) {

    }

    /**
     * 注册用户结果回调接口.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     * @param uid
     *            the 用户id
     * @param token
     *            the 授权令牌
     */
    protected void didRegisterUser(int error, String errorMessage, String uid,
                                   String token) {
        // TODO Auto-generated method stub

    }

    /**
     * 获取ssid列表回调接口.
     *
     * @param error
     *            结果代码
     * @param ssidInfoList
     *            ssid列表
     */
    protected void didGetSSIDList(int error, List<XPGWifiSSID> ssidInfoList) {
        // TODO Auto-generated method stub

    }

    /**
     * 搜索设备回调接口.
     *
     * @param error
     *            结果代码
     * @param devicesList
     *            设备列表
     */
    protected void didDiscovered(int error, List<XPGWifiDevice> devicesList) {
        // TODO Auto-generated method stub

    }

    /**
     * 更换注册手机号码回调接口.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     */
    protected void didChangeUserPhone(int error, String errorMessage) {
        // TODO Auto-generated method stub

    }

    /**
     * 更换密码回调接口.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     */
    protected void didChangeUserPassword(int error, String errorMessage) {
        // TODO Auto-generated method stub

    }

    /**
     * 更换注册邮箱.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     */
    protected void didChangeUserEmail(int error, String errorMessage) {

    }

    /**
     * 绑定设备结果回调.
     *
     * @param error
     *            结果代码
     * @param errorMessage
     *            错误信息
     * @param did
     *            设备注册id
     */
    protected void didBindDevice(int error, String errorMessage, String did) {

    }

    /**
     * 接收指令回调
     * <p/>
     * sdk接收到模块传入的数据回调该接口.
     *
     * @param device
     *            设备对象
     * @param dataMap
     *            json数据表
     * @param result
     *            状态代码
     */
    protected void didReceiveData(XPGWifiDevice device,
                                  ConcurrentHashMap<String, Object> dataMap, int result) {

    }

    /**
     * 登陆设备结果回调接口.
     *
     * @param device
     *            设备对象
     * @param result
     *            状态代码
     */
    protected void didLogin(XPGWifiDevice device, int result) {

    }

    /**
     * 断开连接回调接口.
     *
     * @param device
     *            设备对象
     */
    protected void didDisconnected(XPGWifiDevice device) {

    }

    /**
     * 设备上下线通知.
     *
     * @param device
     *            设备对象
     * @param isOnline
     *            上下线状态
     */
    protected void didDeviceOnline(XPGWifiDevice device, boolean isOnline) {

    }

    /**
     * 通过did和mac在列表寻找对应的device.
     *
     * @param mac
     *            the mac
     * @param did
     *            the did
     * @return the XPG wifi device
     */
    public static XPGWifiDevice findDeviceByMac(String mac, String did) {
        XPGWifiDevice xpgdevice = null;
        Log.i("count", BaseActivity.devicesList.size() + "");
        for (int i = 0; i < BaseActivity.devicesList.size(); i++) {
            XPGWifiDevice device = devicesList.get(i);
            if (device != null) {
                Log.i("deivcemac", device.getMacAddress());
                if (device != null && device.getMacAddress().equals(mac)
                        && device.getDid().equals(did)) {
                    xpgdevice = device;
                    break;
                }
            }

        }

        return xpgdevice;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingManager = new SettingManager(getApplicationContext());
        mCenter = CmdCenter.getInstance(getApplicationContext());
        // 每次返回activity都要注册一次sdk监听器，保证sdk状态能正确回调
        mCenter.getXPGWifiSDK().setListener(sdkListener);
    }

    public void onResume() {
        super.onResume();
        // 每次返回activity都要注册一次sdk监听器，保证sdk状态能正确回调
        mCenter.getXPGWifiSDK().setListener(sdkListener);
    }

    /**
     * 初始化绑定设备列表
     *
     * @return 已绑定设备列表
     */
    protected void initBindList() {
        if (bindList != null && bindList.size() > 0)
            bindList.clear();
        for (XPGWifiDevice xpgDevice : devicesList) {
            if (xpgDevice.isBind(settingManager.getUser().getUid())) {
                bindList.add(xpgDevice);
            }
        }
    }

    public CmdCenter getmCenter() {
        return mCenter;
    }

    public void setmCenter(CmdCenter mCenter) {
        this.mCenter = mCenter;
    }

    public SettingManager getSettingManager() {
        return settingManager;
    }

    public void setSettingManager(SettingManager settingManager) {
        this.settingManager = settingManager;
    }
}
