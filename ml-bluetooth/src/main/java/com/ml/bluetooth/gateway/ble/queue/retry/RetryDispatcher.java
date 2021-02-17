package com.ml.bluetooth.gateway.ble.queue.retry;

import com.ml.bluetooth.gateway.ble.callback.BleConnectCallback;
import com.ml.bluetooth.gateway.ble.request.ConnectRequest;
import com.ml.bluetooth.gateway.ble.request.Rproxy;
import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.BleLog;
import com.ml.bluetooth.gateway.ble.BleStates;
import com.ml.bluetooth.gateway.ble.model.BleDevice;

import java.util.HashMap;
import java.util.Map;



/**
 * author: jerry
 * date: 21-1-8
 * email: superliu0911@gmail.com
 * des:
 */
public class RetryDispatcher<T extends BleDevice> extends BleConnectCallback<T> implements RetryCallback<T> {
    private static final String TAG = "RetryDispatcher";
    private static RetryDispatcher retryDispatcher;
    private final Map<String, Integer> deviceRetryMap = new HashMap<>();

    public static <T extends BleDevice>RetryDispatcher<T> getInstance() {
        if (retryDispatcher == null){
            retryDispatcher = new RetryDispatcher();
        }
        return retryDispatcher;
    }

    @Override
    public void retry(T device) {
        BleLog.i(TAG, "正在尝试重试连接第"+deviceRetryMap.get(device.getBleAddress())+"次重连: "+device.getBleName());
        if (!device.isAutoConnect()){
            ConnectRequest<T> connectRequest = Rproxy.getRequest(ConnectRequest.class);
            connectRequest.connect(device);
        }
    }

    @Override
    public void onConnectionChanged(BleDevice device) {
        BleLog.i(TAG, "onConnectionChanged:"+device.getBleName()+"---连接状态:"+device.isConnected());
        if (device.isConnected()){
            String key = device.getBleAddress();
            deviceRetryMap.remove(key);
        }
    }

    @Override
    public void onConnectFailed(T device, int errorCode) {
        super.onConnectFailed(device, errorCode);
        if (errorCode == BleStates.ConnectError || errorCode == BleStates.ConnectFailed){
            String key = device.getBleAddress();
            int lastRetryCount = Ble.options().connectFailedRetryCount;
            if (lastRetryCount <= 0)return;
            if (deviceRetryMap.containsKey(key)){
                lastRetryCount = deviceRetryMap.get(key);
            }
            if (lastRetryCount <= 0){
                deviceRetryMap.remove(key);
                return;
            }
            deviceRetryMap.put(key, lastRetryCount-1);
            retry(device);
        }
    }
}
