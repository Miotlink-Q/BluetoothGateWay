package com.mlink.bluetooth.gateway.ui;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ml.bluetooth.gateway.ble.Ble;
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

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public
class DeviceControllerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView,back;
    private TextView textView;
    private RecyclerView recyclerView=null;
    private boolean isSwitchState=false;
    public static BleDeviceInfo bleDeviceInfo=null;
    private Ble<BleMLDevice> ble=Ble.getInstance();
    private BleMLDevice bleDevice=null;
    private SubBleManager subBleManager=null;
    SubBleDeviceAdapter subBleDeviceAdapter=null;
    @Override
    public void initView() throws Exception {
        subBleManager=SubBleManager.getInstance(GateWayApplication.getInstance());
        recyclerView=findViewById(R.id.controller_recyclerview);
        imageView=findViewById(R.id.switch_iv);
        back=findViewById(R.id.back_iv);
        textView=findViewById(R.id.device_title_state);
        imageView.setOnClickListener(this);
        back.setOnClickListener(this);
        subBleDeviceAdapter=new SubBleDeviceAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(subBleDeviceAdapter);
        subBleDeviceAdapter.setNewInstance(subBleManager.getSubBleDevices(bleDeviceInfo.getMacCode()));
        if (bleDeviceInfo!=null){
            bleDevice = ble.getBleDevice(bleDeviceInfo.getMacCode());
        }
        if (bleDevice!=null&&bleDevice.isConnected()){
            textView.setText("设备控制(已连接)");
            ble.enableNotify(bleDevice,true,bleNotifyCallback);
        }else {
            ble.connect(bleDeviceInfo.getMacCode(), new BleConnectCallback<BleMLDevice>() {
                @Override
                public void onConnectionChanged(BleMLDevice device) {
                    if (device.isConnected()){
                        textView.setText("设备控制(已连接)");
                    }else if (device.isConnecting()){
                        textView.setText("设备控制(连接中)");
                    }else {
                        textView.setText("设备控制(断开连接)");
                    }
                }

                @Override
                public void onServicesDiscovered(BleMLDevice device, BluetoothGatt gatt) {
                    super.onServicesDiscovered(device, gatt);

                }

                @Override
                public void onReady(BleMLDevice device) {
                    super.onReady(device);
                    if (device.isConnected()){
                        ble.enableNotify(device,true,bleNotifyCallback);
                    }
                }
            });
        }
        subBleDeviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
               SubBleDevice subBleDevice=(SubBleDevice) adapter.getItem(position);
               if (subBleDevice!=null){
                   byte [] bytes=null;
                   if (subBleDevice.getState()==1){
                       bytes=ByteUtils.hexStr2Bytes("FFFA0004"+subBleDevice.getSubId()+"1100");
                   }else {
                       bytes=ByteUtils.hexStr2Bytes("FFFA0004"+subBleDevice.getSubId()+"1101");
                   }
                   BleMLDevice bleDevice = ble.getBleDevice(subBleDevice.getMacCode());

                   if (bleDevice!=null&&bleDevice.isConnected()){
                       ble.writeByUuid(bleDevice, bytes, Ble.options().getUuidService(), Ble.options().getUuidWriteCha(), new BleWriteCallback<BleMLDevice>() {
                           @Override
                           public void onWriteSuccess(BleMLDevice device, BluetoothGattCharacteristic characteristic) {

                           }
                       });
                   }

               }
            }
        });

    }
    BleNotifyCallback<BleMLDevice> bleNotifyCallback=new BleNotifyCallback<BleMLDevice>() {
        @Override
        public void onChanged(BleMLDevice device, BluetoothGattCharacteristic characteristic) {
            UUID uuid = characteristic.getUuid();
            if (uuid.equals(Ble.options().getUuidReadCha())){
                byte[] value = characteristic.getValue();
                String string= ByteUtils.toHexString(value);
                if (string.startsWith("fffa")){
                    String[] values = ByteUtils.hexStr2Strings(string);
                    if (values[6]=="12"){

                        if (values[7]=="01"){
                            //全亮
                            subBleManager.updateSubDeviceState(1,device.getBleAddress(),"");
                        }else {
                            //全灭
                            subBleManager.updateSubDeviceState(0,device.getBleAddress(),"");
                        }
                    }else if (values[6]=="11"){
                        if (values[7]=="01"){
                            subBleManager.updateSubDeviceState(1,device.getBleAddress(),values[4]+values[5]);
                        }else {
                            //全灭
                            subBleManager.updateSubDeviceState(0,device.getBleAddress(),values[4]+values[5]);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            subBleDeviceAdapter.setNewInstance(subBleManager.getSubBleDevices(bleDevice.getBleAddress()));
                        }
                    });

                }

            }
        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_controller_device;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.switch_iv:
                BleMLDevice bleDevice = ble.getBleDevice(bleDeviceInfo.getMacCode());
                if(bleDevice==null){
                    return;
                }
                byte[] bytes=null;
                if (bleDevice.isConnected()){
                    bytes=ByteUtils.hexStr2Bytes("FFFA000400001200");
                    if (isSwitchState){
                        ble.writeByUuid(bleDevice,bytes,Ble.options().getUuidService(),Ble.options().getUuidWriteCha(), new BleWriteCallback<BleMLDevice>() {
                            @Override
                            public void onWriteSuccess(BleMLDevice device, BluetoothGattCharacteristic characteristic) {

                            }
                        });
                    }else {
                        bytes=ByteUtils.hexStr2Bytes("FFFA000400001201");
                        ble.writeByUuid(bleDevice,bytes,Ble.options().getUuidService(),Ble.options().getUuidWriteCha(), new BleWriteCallback<BleMLDevice>() {
                            @Override
                            public void onWriteSuccess(BleMLDevice device, BluetoothGattCharacteristic characteristic) {

                            }
                        });
                    }
                }
                break;
        }
    }
}
