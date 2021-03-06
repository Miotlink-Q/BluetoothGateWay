package com.mlink.bluetooth.gateway.ui;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.BleLog;
import com.ml.bluetooth.gateway.ble.callback.BleConnectCallback;
import com.ml.bluetooth.gateway.ble.callback.BleNotifyCallback;
import com.ml.bluetooth.gateway.ble.callback.BleWriteCallback;
import com.ml.bluetooth.gateway.ble.utils.ByteUtils;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.adapter.SubBleDeviceAdapter;
import com.mlink.bluetooth.gateway.application.GateWayApplication;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;
import com.mlink.bluetooth.gateway.db.SubBleManager;
import com.mlink.bluetooth.gateway.view.RadarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddSubDeviceActivity extends BaseActivity {

    public static BleMLDevice bleMLDevice = null;
    private Ble<BleMLDevice> ble = Ble.getInstance();
    private String subId = "";

    private MyThread myThread = null;

    SubBleManager subBleManager = null;

    private String level = "0";

    private SubBleDeviceAdapter subBleDeviceAdapter = null;




    private RadarView radarView = null;

    private RecyclerView recyclerView = null;

    private ImageView startIv=null;

    private TextView connectTv=null;

    private boolean isConnect=false;

    @Override
    public void initView() throws Exception {
        startIv=findViewById(R.id.start_iv);
        connectTv=findViewById(R.id.device_connect_tv);
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!isConnect){
                   Toast.makeText(mContext, "连接已断开", Toast.LENGTH_LONG).show();
               }
                startIv.setImageResource(R.mipmap.start_2);
                if (myThread != null) {
                    myThread.interrupt();
                    myThread=null;
                }
                myThread=new MyThread();
                myThread.setRunning(true);
                subId = "FFFF";
                myThread.start();
            }
        });
        radarView = findViewById(R.id.radar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        subBleManager = SubBleManager.getInstance(GateWayApplication.getInstance());
        subBleDeviceAdapter = new SubBleDeviceAdapter();

        recyclerView.setAdapter(subBleDeviceAdapter);
        List<SubBleDevice> subBleDevices = subBleManager.getSubBleDevices(bleMLDevice.getBleAddress());
        if (subBleDevices!=null){
            subBleDeviceAdapter.setNewInstance(subBleDevices);
        }
        subBleDeviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SubBleDevice subBleDevice = (SubBleDevice) adapter.getItem(position);
                if (subBleDevice != null) {
//                    String value = "";
//                    if (subBleDevice.getState() == 0) {
//                        value = "FFFA0004" + subId + "1101";
//                        subBleManager.updateSubDeviceState(0, subBleDevice.getMacCode(), subBleDevice.getSubId());
//                    } else {
//                        value = "FFFA0004" + subId + "1100";
//                        subBleManager.updateSubDeviceState(0, subBleDevice.getMacCode(), subBleDevice.getSubId());
//                    }
//                    byte[] bytes = ByteUtils.hexStr2Bytes(value);
//                    ble.writeByUuid(bleMLDevice, bytes, Ble.options().getUuidService(), Ble.options().getUuidWriteCha(), bleWriteCallback);
//                    subBleDeviceAdapter.setNewInstance(subBleManager.getSubBleDevices(subBleDevice.getMacCode()));
                    if (!isConnect){
                        Toast.makeText(mContext, "连接已断开", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (myThread != null) {
                        myThread.interrupt();
                        myThread = null;
                    }
                    subId = subBleDevice.getSubId();
                    subBleManager.updateSubDeviceNodeState(1,
                            subBleDevice.getMacCode(),
                            subId);
                    if (!TextUtils.isEmpty(subId) && subId.startsWith("00")) {
                        level = subId.substring(2, subId.length());
                    }
                    myThread = new MyThread();
                    myThread.start();
                }
            }
        });
        myThread = new MyThread();

        List<BleMLDevice> connectedDevices = ble.getConnectedDevices();
        if (connectedDevices != null && connectedDevices.size() > 0) {
            for (BleMLDevice ble : connectedDevices) {
                if (bleMLDevice.getBleAddress().equals(ble.getBleAddress())) {
                    isConnect = true;
                    connectTv.setText("(已连接)");
                    break;
                }
            }
        }
        if (isConnect) {
            ble.enableNotify(bleMLDevice, true, bleMLDeviceBleNotifyCallback);
        } else {
            ble.connect(bleMLDevice, new BleConnectCallback<BleMLDevice>() {
                @Override
                public void onConnectionChanged(BleMLDevice device) {
                    if (device.isConnected()){
                        isConnect=true;
                        connectTv.setText("(已连接)");
                    }else if (device.isConnecting()){
                        isConnect=false;
                        connectTv.setText("(连接中)");
                    }else if (device.isDisconnected()){
                        connectTv.setText("(连接已断开)");
                        isConnect=false;
                    }else {
                        connectTv.setText("(连接失败)");
                    }
                }

                @Override
                public void onServicesDiscovered(BleMLDevice device, BluetoothGatt gatt) {
                    super.onServicesDiscovered(device, gatt);

                }

                @Override
                public void onReady(BleMLDevice device) {
                    super.onReady(device);
                    ble.enableNotify(device, true, bleMLDeviceBleNotifyCallback);
//                    if (device.isConnected()) {
//                        if (myThread != null) {
//                            myThread.setRunning(true);
//
//                        }else {
//                            myThread=new MyThread();
//                            myThread.setRunning(true);
//                        }
//                        subId = "FFFF";
//                        myThread.start();
//                    }
                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (radarView != null) {
            radarView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        subId = "";
        if (myThread != null) {
            myThread.interrupt();
            myThread = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (radarView != null) {
            radarView.stop();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_device_sub;
    }

    class MyThread extends Thread {

        boolean isRunning = false;

        public void setRunning(boolean running) {
            isRunning = running;
        }

        @Override
        public void run() {
            super.run();
            try {
                if (isRunning) {
                    Thread.sleep(3000);
                }
                if (!TextUtils.isEmpty(subId)) {
                    String sub = "FFFA0004" + subId + "0100";
                    BleLog.e("gateway", sub);
                    byte[] bytes = ByteUtils.hexStr2Bytes(sub);
                    ble.writeByUuid(bleMLDevice, bytes, Ble.options().getUuidService(), Ble.options().getUuidWriteCha(), bleWriteCallback);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            while (isRunning) {
//                try {
////                    if ((!TextUtils.isEmpty(subId)&&subDeviceStr.size()==2)||
////                            (!TextUtils.isEmpty(subId)&&subDeviceStr.size()==0
////                                    &&TextUtils.equals(subId, "FFFF"))){
////                        byte [] bytes= ByteUtils.hexStr2Bytes("FFFA0004"+subId+"0100");
////                        ble.writeByUuid(bleMLDevice,bytes,Ble.options().getUuidService(),Ble.options().getUuidWriteCha(),bleWriteCallback);
////                        subId="";
////                    }
//
//                    if (!TextUtils.isEmpty(subId)) {
//                        String sub = "FFFA0004" + subId + "0100";
//                        BleLog.e("gateway", sub);
//                        byte[] bytes = ByteUtils.hexStr2Bytes(sub);
//                        ble.writeByUuid(bleMLDevice, bytes, Ble.options().getUuidService(), Ble.options().getUuidWriteCha(), bleWriteCallback);
//                        subId = "";
//                    }
//                    Thread.sleep(15000);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    BleWriteCallback bleWriteCallback = new BleWriteCallback() {
        @Override
        public void onWriteSuccess(Object device, BluetoothGattCharacteristic characteristic) {

        }
    };

    BleNotifyCallback<BleMLDevice> bleMLDeviceBleNotifyCallback = new BleNotifyCallback<BleMLDevice>() {
        @Override
        public void onChanged(BleMLDevice device, BluetoothGattCharacteristic characteristic) {
            if (characteristic != null) {
                UUID uuid = characteristic.getUuid();

                if (uuid.equals(Ble.options().getUuidReadCha())) {
                    byte[] value = characteristic.getValue();
                    String s = ByteUtils.bytes2HexStr(value);
                    BleLog.e("error", s);
                    String[] strings = ByteUtils.bytes2HexStrings(value);
                    if (!TextUtils.isEmpty(s) && s.startsWith("fffa") && s.endsWith("03")) {

                        BleLog.e("value", strings.toString());
                        if (strings != null && strings.length > 0) {
                            SubBleDevice subBleDevice = new SubBleDevice();
                            subBleDevice.setSubId(strings[4] + strings[5]);
                            subBleDevice.setMacCode(device.getBleAddress());
                            subBleDevice.setLevel(level);
                            subBleManager.addSubBleDevice(subBleDevice);
                            BleDeviceInfo bleDeviceInfo = new BleDeviceInfo();
                            bleDeviceInfo.setId(device.getBleAddress().replaceAll(":", ""));
                            bleDeviceInfo.setState(1);
                            bleDeviceInfo.setMacCode(device.getBleAddress());
                            subBleManager.addDevice(bleDeviceInfo);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<SubBleDevice> subBleDevices = subBleManager.getSubBleDevices(bleMLDevice.getBleAddress());
                                    if (subBleDevices != null) {
                                        BleLog.e("subBleDevices", subBleDevices.toString());
                                        subBleDeviceAdapter.setNewInstance(subBleDevices);
                                    }
                                }
                            });

//                            if (TextUtils.isEmpty(subId)) {
//                                subId = strings[4] + strings[5];
//                            }
                        }
                    } else if (strings.length >= 8 && strings[6].equals("11")) {
                        if (strings[7].equals("01")) {
                            subBleManager.updateSubDeviceState(1, bleMLDevice.getBleAddress(),
                                    strings[4] + strings[5]);
                        } else {
                            subBleManager.updateSubDeviceState(0, bleMLDevice.getBleAddress(),
                                    strings[4] + strings[5]);
                        }

                    }
                }
            }
        }
    };
}
