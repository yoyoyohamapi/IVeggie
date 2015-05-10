package com.iveggie.framework.helper;

import android.content.Context;

import com.iveggie.framework.config.Configs;
import com.xtremeprog.xpgconnect.XPGWifiSDK;
import com.xtremeprog.xpgconnect.XPGWifiSDKListener;

/**
 * GoKitHelper
 * Desc:
 * Team: Rangers
 * Date: 2015/5/9
 * Time: 19:32
 * Created by: Wooxxx
 */
public class GoKitHelper {
    /**
     * 初始化机智云
     *
     * @param ctx 上下文环境
     */
    public static void initGokit(Context ctx) {
        // 初始化SDK
        XPGWifiSDK.sharedInstance().startWithAppID(ctx, Configs.APPID);
        // 初始化日志
        XPGWifiSDK.sharedInstance().setLogLevel(
                Configs.LOG_LEVEL,
                Configs.LOG_FILE_NAME,
                Configs.DEBUG
        );
    }


}
