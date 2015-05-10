package com.iveggie.framework.config;

import com.xtremeprog.xpgconnect.XPGWifiSDK;

/**
 * Configs
 * Desc: 机智云配置
 * Date: 2015/5/10
 * Time: 12:44
 * Created by: Wooxxx
 */
public class Configs {
    /**  设备名字符显示长度. */
    public static final int DEVICE_NAME_KEEP_LENGTH = 8;

    /**  设定是否为debug版本. */
    public static final boolean DEBUG = true;

    /**  设定AppID，参数为机智云官网中查看产品信息得到的AppID. */
    public static final String APPID = "c7d0736ffa8b4880bd708c9cc2d8849d";

    /**  指定该app对应设备的product_key，如果设定了过滤，会过滤出该peoduct_key对应的设备. */
    public static final String PRODUCT_KEY = "47448aa36451463fa67c4c5662a2699c";

    /**  设定日志打印级别. */
    public static final XPGWifiSDK.XPGWifiLogLevel LOG_LEVEL = XPGWifiSDK.XPGWifiLogLevel.XPGWifiLogLevelAll;

    /**  日志保存文件名. */
    public static final String LOG_FILE_NAME = "BassApp.log";
}
