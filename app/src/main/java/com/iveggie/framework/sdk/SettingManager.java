package com.iveggie.framework.sdk;

import android.content.Context;
import android.content.SharedPreferences;

import com.iveggie.framework.entity.User;


/**
 * SettingManager
 * Desc: SharePreference处理类.
 * Date: 2015/5/10
 * Time: 11:09
 * Created by: Wooxxx
 */
public class SettingManager {

    /**
     * 用户设置
     */
    SharedPreferences spf;

    private Context ctx;

    // Sharepreference文件的名字
    /** The share preferences. */
    private final String SHARE_PREFERENCES = "set";
    // 用户名
    /** The user name. */
    private final String USER_NAME = "username";
    // 手机号码
    /** The phone num. */
    private final String PHONE_NUM = "phonenumber";
    // 密码
    /** The password. */
    private final String PASSWORD = "password";
    // 用户名
    /** The token. */
    private final String TOKEN = "token";
    // 用户ID
    /** The uid. */
    private final String UID = "uid";

    /** The unit. */
    private final String UNIT = "unit";

    /** The filter. */
    static String filter = "=====";

    public SettingManager(Context ctx) {
        this.ctx = ctx;
        spf = ctx.getSharedPreferences(SHARE_PREFERENCES,Context.MODE_PRIVATE);
    }

    /**
     * SharePreference clean.
     */
    public void clean() {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(USER_NAME,"");
        editor.putString(PHONE_NUM, "");
        editor.putString(PASSWORD, "");
        editor.putString(TOKEN, "");
        editor.putString(UID,"");
        editor.commit();
    }

    /**
     * 设置yonghu
     * @param user
     */
    public void setUser(User user){
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(USER_NAME,user.getUsername());
        editor.putString(PHONE_NUM, user.getPhone());
        editor.putString(PASSWORD, user.getPwd());
        editor.putString(TOKEN, user.getToken());
        editor.putString(UID, user.getUid());
        editor.commit();
    }

    public User getUser(){
                return new User(
                        spf.getString(USER_NAME,""),
                        spf.getString(PHONE_NUM,""),
                        spf.getString(PASSWORD,""),
                        spf.getString(TOKEN,""),
                        spf.getString(UID,"")
                );
    }

}
