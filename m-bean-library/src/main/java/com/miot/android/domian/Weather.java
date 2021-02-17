package com.miot.android.domian;

/**
 * Created by Administrator on 2019/9/24 0024.
 */

public class Weather {

    /**
     * city : 浙江省,杭州市
     * enCity : Hangzhou
     * miotAqi : 69
     * miotEnWeather : Sunny
     * miotIcon : http://statics.51miaomiao.com/statics/docs/weather/00.png
     * miotO3 : 175
     * miotPM2_5 : 26
     * miotSD : 37
     * miotTempDay : 29
     * miotTempNight : 18
     * miotTemperature : 28
     * miotWeather : 晴天
     * miotWindDirection : 西北风
     * miotWindPower : 2级
     * refreshing : false
     * refreshingTime : 0
     * resultCode : 1
     * time : 1569308290217
     */

    private String city;
    private String enCity;
    private String miotAqi;
    private String miotEnWeather;
    private String miotIcon;
    private String miotO3;
    private String miotPM2_5;
    private String miotSD;
    private String miotTempDay;
    private String miotTempNight;
    private String miotTemperature;
    private String miotWeather;
    private String miotWindDirection;
    private String miotWindPower;
    private boolean refreshing;
    private int refreshingTime;
    private String resultCode;
    private long time;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEnCity() {
        return enCity;
    }

    public void setEnCity(String enCity) {
        this.enCity = enCity;
    }

    public String getMiotAqi() {
        return miotAqi;
    }

    public void setMiotAqi(String miotAqi) {
        this.miotAqi = miotAqi;
    }

    public String getMiotEnWeather() {
        return miotEnWeather;
    }

    public void setMiotEnWeather(String miotEnWeather) {
        this.miotEnWeather = miotEnWeather;
    }

    public String getMiotIcon() {
        return miotIcon;
    }

    public void setMiotIcon(String miotIcon) {
        this.miotIcon = miotIcon;
    }

    public String getMiotO3() {
        return miotO3;
    }

    public void setMiotO3(String miotO3) {
        this.miotO3 = miotO3;
    }

    public String getMiotPM2_5() {
        return miotPM2_5;
    }

    public void setMiotPM2_5(String miotPM2_5) {
        this.miotPM2_5 = miotPM2_5;
    }

    public String getMiotSD() {
        return miotSD;
    }

    public void setMiotSD(String miotSD) {
        this.miotSD = miotSD;
    }

    public String getMiotTempDay() {
        return miotTempDay;
    }

    public void setMiotTempDay(String miotTempDay) {
        this.miotTempDay = miotTempDay;
    }

    public String getMiotTempNight() {
        return miotTempNight;
    }

    public void setMiotTempNight(String miotTempNight) {
        this.miotTempNight = miotTempNight;
    }

    public String getMiotTemperature() {
        return miotTemperature;
    }

    public void setMiotTemperature(String miotTemperature) {
        this.miotTemperature = miotTemperature;
    }

    public String getMiotWeather() {
        return miotWeather;
    }

    public void setMiotWeather(String miotWeather) {
        this.miotWeather = miotWeather;
    }

    public String getMiotWindDirection() {
        return miotWindDirection;
    }

    public void setMiotWindDirection(String miotWindDirection) {
        this.miotWindDirection = miotWindDirection;
    }

    public String getMiotWindPower() {
        return miotWindPower;
    }

    public void setMiotWindPower(String miotWindPower) {
        this.miotWindPower = miotWindPower;
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
    }

    public int getRefreshingTime() {
        return refreshingTime;
    }

    public void setRefreshingTime(int refreshingTime) {
        this.refreshingTime = refreshingTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
