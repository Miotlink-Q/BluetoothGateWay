package com.mlink.bluetooth.gateway.application;

import android.app.Application;


import com.miot.android.data.d.db.DBUtils.DataManager;
import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.BleLog;
import com.ml.bluetooth.gateway.ble.model.BleFactory;
import com.ml.bluetooth.gateway.ble.utils.UuidUtils;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;

import java.util.UUID;

public
class GateWayApplication extends Application {


    private static GateWayApplication instance=null;


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        DataManager.cazz= new Class[]{BleDeviceInfo.class, SubBleDevice.class};
        Ble.options()
                .setLogBleEnable(true)//设置是否输出打印蓝牙日志
                .setThrowBleException(true)//设置是否抛出蓝牙异常
                .setLogTAG("AndroidBLE")//设置全局蓝牙操作日志TAG
                .setAutoConnect(false)//设置是否自动连接
                .setIgnoreRepeat(false)//设置是否过滤扫描到的设备(已扫描到的不会再次扫描)
                .setConnectFailedRetryCount(3)//连接异常时（如蓝牙协议栈错误）,重新连接次数
                .setConnectTimeout(10 * 1000)//设置连接超时时长
                .setScanPeriod(12 * 1000)//设置扫描时长
                .setMaxConnectNum(7)//最大连接数量
                .setUuidService(UUID.fromString(UuidUtils.uuid16To128("fff0")))//设置主服务的uuid
                .setUuidWriteCha(UUID.fromString(UuidUtils.uuid16To128("fff1")))//设置可写特征的uuid
                .setUuidReadCha(UUID.fromString(UuidUtils.uuid16To128("fff4")))//设置可读特征的uuid （选填）
                .setUuidNotifyCha(UUID.fromString(UuidUtils.uuid16To128("fff4")))//设置可通知特征的uuid （选填，库中默认已匹配可通知特征的uuid）
                .setFactory(new BleFactory<BleMLDevice>() {//实现自定义BleDevice时必须设置
                    @Override
                    public BleMLDevice create(String address, String name) {
                        return new BleMLDevice(address, name);//自定义BleDevice的子类
                    }
                })
                .create(this, new Ble.InitCallback() {
                    @Override
                    public void success() {
                        BleLog.e("MainApplication", "初始化成功");
                    }

                    @Override
                    public void failed(int failedCode) {
                        BleLog.e("MainApplication", "初始化失败：" + failedCode);
                    }
                });

    }

    public static GateWayApplication getInstance() {
        return instance;
    }
}
