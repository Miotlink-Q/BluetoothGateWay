package com.ml.bluetooth.gateway.ble.queue;



import com.ml.bluetooth.gateway.ble.BleRequestImpl;
import com.ml.bluetooth.gateway.ble.model.BleDevice;

import androidx.annotation.NonNull;


public final class WriteQueue extends Queue{

    private static volatile WriteQueue sInstance;
    protected BleRequestImpl bleRequest;

    private WriteQueue() {
        bleRequest = BleRequestImpl.getBleRequest();
    }

    @NonNull
    public static WriteQueue getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (WriteQueue.class) {
            if (sInstance == null) {
                sInstance = new WriteQueue();
            }
        }
        return sInstance;
    }

    @Override
    public void execute(RequestTask requestTask) {
        BleDevice[] devices = requestTask.getDevices();
        for (BleDevice device: devices) {
            bleRequest.writeCharacteristic(device.getBleAddress(), requestTask.getData());
        }
    }

}
