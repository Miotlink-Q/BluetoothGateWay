package com.mlink.bluetooth.gateway.ui;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.text.TextUtils;

import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.BleLog;
import com.ml.bluetooth.gateway.ble.callback.BleConnectCallback;
import com.ml.bluetooth.gateway.ble.callback.BleNotifyCallback;
import com.ml.bluetooth.gateway.ble.callback.BleWriteCallback;
import com.ml.bluetooth.gateway.ble.utils.ByteUtils;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddSubDeviceActivity extends BaseActivity {

    public static BleMLDevice bleMLDevice=null;
    private Ble<BleMLDevice> ble = Ble.getInstance();
    private String subId="";
    private boolean isRunning=false;
    private MyThread myThread=null;
    private List<Integer> integers=new ArrayList<>();

    @Override
    public void initView() throws Exception {
        myThread=new MyThread();
        boolean isConnect=false;
        List<BleMLDevice> connectedDevices = ble.getConnectedDevices();
        if (connectedDevices!=null&&connectedDevices.size()>0){
            for (BleMLDevice ble:connectedDevices) {
                if (bleMLDevice.getBleAddress().equals(ble.getBleAddress())){
                    isConnect=true;
                    break;
                }
            }
        }

        if (isConnect){
            if (myThread!=null){
                isRunning=true;
                subId="FFFF";
                myThread.start();
            }
        }else {


        ble.connect(bleMLDevice, new BleConnectCallback<BleMLDevice>() {
            @Override
            public void onConnectionChanged(BleMLDevice device) {

            }

            @Override
            public void onServicesDiscovered(BleMLDevice device, BluetoothGatt gatt) {
                super.onServicesDiscovered(device, gatt);
                if (device.isConnected()){

                    if (myThread!=null){
                        isRunning=true;
                        subId="FFFF";
                        myThread.start();

                    }
                }
            }

            @Override
            public void onReady(BleMLDevice device) {
                super.onReady(device);
                ble.enableNotify(device, true, new BleNotifyCallback<BleMLDevice>() {
                    @Override
                    public void onChanged(BleMLDevice device, BluetoothGattCharacteristic characteristic) {
                        if (characteristic!=null){
                            UUID uuid = characteristic.getUuid();

                            if (uuid.equals(Ble.options().getUuidReadCha())) {
                                byte[] value = characteristic.getValue();

                                String s = ByteUtils.bytes2HexStr(value);
                                BleLog.e("error", s);
                                if (TextUtils.isEmpty(s)&&s.startsWith("fffa")&&s.endsWith("03")){
                                    String[] strings = ByteUtils.hexStr2Strings(s);
                                    if (strings!=null&&strings.length>0){
                                        int len=Integer.parseInt(strings[2]+strings[3],16);
                                        int id=Integer.parseInt(strings[4]+strings[5],16);
                                        if (!integers.contains(id)){
                                            integers.add(id);
                                        }
                                        if (TextUtils.isEmpty(subId)){
                                            subId=strings[4]+strings[5];
                                        }
                                    }
                                }
                            }
                        }

                    }
                });
            }
        });

        }

    }

    @Override
    public int getContentView() {
        return R.layout.activity_device_sub;
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (isRunning){
                try {
                    if (!TextUtils.isEmpty(subId)){
                        byte [] bytes= ByteUtils.hexStr2Bytes("FFFA0004"+subId+"0100");
                        ble.writeByUuid(bleMLDevice,bytes,Ble.options().getUuidService(),Ble.options().getUuidWriteCha(),bleWriteCallback);
                        subId="";
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    BleWriteCallback bleWriteCallback=new BleWriteCallback() {
        @Override
        public void onWriteSuccess(Object device, BluetoothGattCharacteristic characteristic) {

        }
    };
}
