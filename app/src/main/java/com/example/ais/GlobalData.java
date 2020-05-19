package com.example.ais;

import android.app.Application;

/**
 * Application，存储全局accessToken
 */
public class GlobalData extends Application{
    private String accessToken;
    private static final String ACCESSTOKEN = "ErrorAccessToken";
    @Override
    public void onCreate()
    {
        super.onCreate();
        setAccessToken(ACCESSTOKEN); // 初始化全局变量
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }
}
