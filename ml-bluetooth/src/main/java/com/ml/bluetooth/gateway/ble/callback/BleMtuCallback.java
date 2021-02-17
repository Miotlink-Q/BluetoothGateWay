package com.ml.bluetooth.gateway.ble.callback;


import com.ml.bluetooth.gateway.ble.model.BleDevice;

/**
 * Created by LiuLei on 2018/6/2.
 */

public abstract class BleMtuCallback<T> {

    public void onMtuChanged(BleDevice device, int mtu, int status){}

}
