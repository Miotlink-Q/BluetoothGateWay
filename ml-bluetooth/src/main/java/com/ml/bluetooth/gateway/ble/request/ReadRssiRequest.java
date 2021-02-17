package com.ml.bluetooth.gateway.ble.request;


import com.ml.bluetooth.gateway.ble.BleRequestImpl;
import com.ml.bluetooth.gateway.ble.annotation.Implement;
import com.ml.bluetooth.gateway.ble.callback.BleReadRssiCallback;
import com.ml.bluetooth.gateway.ble.callback.wrapper.ReadRssiWrapperCallback;
import com.ml.bluetooth.gateway.ble.model.BleDevice;

/**
 *
 * Created by LiuLei on 2017/10/23.
 */
@Implement(ReadRssiRequest.class)
public class ReadRssiRequest<T extends BleDevice> implements ReadRssiWrapperCallback<T> {

    private BleReadRssiCallback<T> readRssiCallback;
    private final BleRequestImpl<T> bleRequest = BleRequestImpl.getBleRequest();

    public boolean readRssi(T device, BleReadRssiCallback<T> callback){
        this.readRssiCallback = callback;
        boolean result = false;
        if (bleRequest != null) {
            result = bleRequest.readRssi(device.getBleAddress());
        }
        return result;
    }

    @Override
    public void onReadRssiSuccess(T device, int rssi) {
        if(readRssiCallback != null){
            readRssiCallback.onReadRssiSuccess(device, rssi);
        }
    }
}
