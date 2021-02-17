package com.miot.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.miot.android.orm.Cu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import androidx.annotation.NonNull;

/**
 *
 * 数据存储
 */
public class SPManager {

    public static final String LOGIN="LOGIN";
    public static final String USERAGREEMENT="USERAGREEMENT";

    /**
     * The settings.
     */
    private SharedPreferences settings;

    /**
     * The Constant sPrefsFileName.
     */
    public static final String sPrefsFileName = "Miot_PrefsFile";

    /**
     * The editor.
     */
    private SharedPreferences.Editor editor;

    /**
     * The instance.
     */
    private static SPManager instance;

    /**
     * The lock.
     */
    private final Lock lock = new ReentrantLock();

    private static String currentAccount="";

    Gson gson=new Gson();


    /**
     * Gets the single instance of SharedPreferencesUtil.
     *
     * @param context the context
     * @return single instance of SharedPreferencesUtil
     */
    public static SPManager getInstance(@NonNull Context context) {
        return getInstance(context, context.getPackageName()+".common");
    }
    public static synchronized SPManager getInstance() {
        if (instance==null){
            synchronized (SPManager.class){
                if (instance==null){
                    instance=new SPManager();
                }
            }

        }
        return instance;
    }
    public void init(Context mContext){
        getInstance(mContext, mContext.getPackageName()+".common");
    }
    private SPManager(){
    }

    private String ADVERTISING_MSG="AdvertisingMsg";

    public void setAdvertisingMsg(String advertising){
        editor.putString(ADVERTISING_MSG,advertising);
        editor.commit();
    }

    public String getAdvertisingMsg(){
        return settings.getString(ADVERTISING_MSG,"");
    }


    public void saveStringData(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }
    public String getStringData(String key){

        return settings.getString(key,"");
    }
    public void saveBooleanData(String key,boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }
    public boolean getBooleanData(String key){
        return settings.getBoolean(key,false);
    }



    /**
     * Instantiates a new shared preferences util.
     *
     * @param context the context
     * @param account the account
     */
    private SPManager(@NonNull Context context, String account) {
        String fileName = sPrefsFileName;
        currentAccount = account;
        if (!TextUtils.isEmpty(account)) { // 如果有用户名，则各自存储，否则存在公用的
            fileName = account + sPrefsFileName;

        } else {
            throw new IllegalArgumentException("account can not be empty.");
        }
        lock.lock();
        try {
            settings = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
            editor = settings.edit();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    /**
     * 配置是不区分用户名存储的，即所有用户都共享这份存储.
     *
     * @param context the context
     * @param account the account
     * @return single instance of SharedPreferencesUtil
     */
    public static SPManager getInstance(@NonNull Context context, @NonNull String account) {
        if (instance == null) {
            instance = new SPManager(context, account);
        } else {
            if (TextUtils.isEmpty(account)) {
                throw new IllegalArgumentException("account can not be empty.");
            } else if (!account.equals(currentAccount)) {
                instance = new SPManager(context, account);
            }
        }
        return instance;
    }

    private final static String AUTO_LOGON = "auto_logon";
    private final static String First="FIRST";

    private final static String USER="USER";

    private final static String ISLOGIN="IsLogin";
    private final static String LANGUAGE = "Language";

    private final static String BAIDU_LOCATION="BAIDU_LOCATION";
    /**
     * Gets the shared preferences.
     *
     * @return the shared preferences
     */
    public SharedPreferences getSharedPreferences() {
        return settings;
    }

    /**
     * Gets the editor.
     *
     * @return the editor
     */
    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    /**
     * 清空所有数据.
     */
    public void clearAllData() {
        editor.clear();
        editor.commit();
    }


    public void putFirst(boolean isFirst){
        editor.putBoolean(First,isFirst);
        editor.commit();
    }

    public boolean getFirst(){

        return settings.getBoolean(First,false);
    }


    public void setCu(Cu cu){
        editor.putString(USER,gson.toJson(cu));
        editor.commit();
    }

    public Cu getCu(){
       String user=settings.getString(USER,"");
       if (TextUtils.isEmpty(user)){
           return null;
       }
       return gson.fromJson(user,Cu.class);
    }

    public void setIsLogin(boolean isLogin){
        editor.putBoolean(ISLOGIN,isLogin);
        editor.commit();
    }

    public boolean getIsLogin(){
        return settings.getBoolean(ISLOGIN,false);
    }

    public void setRouteName(String routeName,String password){
        editor.putString(routeName,password);
        editor.commit();
    }
    public String setRoutePass(String routeName){

        return settings.getString(routeName,"");
    }


    public void setBaiDuLocation(String location){
        editor.putString(BAIDU_LOCATION,location);
        editor.commit();
    }

    public void setAutoLogon(boolean isAutoLogon) {
        editor.putBoolean(AUTO_LOGON, isAutoLogon);
        editor.commit();
    }

    public boolean getAutoLogon() {
        return settings.getBoolean(AUTO_LOGON, false);
    }

    public int getLanguage() {
        return settings.getInt(LANGUAGE, 0);
    }
    public void setLanguage(int value) {
        editor.putInt(LANGUAGE, value);
        editor.commit();
    }


    public String getLocation(){
        return settings.getString(BAIDU_LOCATION,"");
    }


    private String FRAMEWORK_VERSIONID="framework_versionId";

    public void setFrameworkVersionId(String versionId){
        editor.putString(FRAMEWORK_VERSIONID,versionId);
        editor.commit();
    }

    public String getFrameworkVersionId(){
        return settings.getString(FRAMEWORK_VERSIONID,"0");
    }

    private String BASE_FRAMEWORK_VERSIONID="baseFramework_versionId";

    public void setBaseFrameworkVersionId(String versionId){
        editor.putString(BASE_FRAMEWORK_VERSIONID,versionId);
        editor.commit();
    }

    public String getBaseFrameworkVersionId(){
        return settings.getString(BASE_FRAMEWORK_VERSIONID,"0");
    }
}
