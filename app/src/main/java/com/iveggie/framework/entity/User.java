package com.iveggie.framework.entity;

/**
 * User
 * Desc:
 * Date: 2015/5/10
 * Time: 12:45
 * Created by: Wooxxx
 */
public class User extends Base {
    private String phone;
    private String pwd;
    private String token;
    private String uid;
    private String username;


    public User() {
    }

    public User(String username,String phone, String pwd, String token, String uid) {
        this.phone = phone;
        this.pwd = pwd;
        this.token = token;
        this.uid = uid;
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
