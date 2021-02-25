package com.mlink.bluetooth.gateway.ui;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.text.TextUtils;
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
import com.mlink.bluetooth.gateway.bean.TreeSubBleDevice;
import com.mlink.bluetooth.gateway.db.SubBleManager;
import com.mlink.bluetooth.gateway.view.ViewHolder;
import com.tencent.mmkv.MMKV;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeView;

public
class DeviceControllerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView,back;
    private TextView textView;
    private RecyclerView recyclerView=null;

    public static BleDeviceInfo bleDeviceInfo=null;
    private Ble<BleMLDevice> ble=Ble.getInstance();
    private BleMLDevice bleDevice=null;
    private SubBleManager subBleManager=null;
    SubBleDeviceAdapter subBleDeviceAdapter=null;
    private TreeView treeView;
    private BaseTreeAdapter adapter = null;
    @Override
    public void initView() throws Exception {
        subBleManager=SubBleManager.getInstance(GateWayApplication.getInstance());
        recyclerView=findViewById(R.id.controller_recyclerview);
        treeView = findViewById(R.id.treeView);
        imageView=findViewById(R.id.switch_iv);
        back=findViewById(R.id.back_iv);
        textView=findViewById(R.id.device_title_state);
        imageView.setOnClickListener(this);
        back.setOnClickListener(this);

        subBleDeviceAdapter=new SubBleDeviceAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(subBleDeviceAdapter);
        subBleDeviceAdapter.setNewInstance(subBleManager.getSubBleDevices(bleDeviceInfo.getMacCode()));
        boolean isSwitchState=MMKV.defaultMMKV().getBoolean("ALL_STATE"+bleDeviceInfo.getMacCode(), true);
        if (isSwitchState){
            imageView.setImageResource(R.mipmap.switch_open_1);
        }else {
            imageView.setImageResource(R.mipmap.switch_close);
        }
        adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.item_node_sub_device) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {

                if (data instanceof SubBleDevice) {
                    SubBleDevice subBleDevice = (SubBleDevice) data;
                    if (!TextUtils.isEmpty(subBleDevice.getSubId())
                            &&subBleDevice.getSubId().length()>2){
                        if (subBleDevice.getSubId().startsWith("FF")
                                ||subBleDevice.getSubId().startsWith("00")){
                            viewHolder.mTextView.setText(subBleDevice.getSubId().substring(2,subBleDevice.getSubId().length()));
                        }else {
                            viewHolder.mTextView.setText(subBleDevice.getSubId());
                        }
                    }else {
                        viewHolder.mTextView.setText(subBleDevice.getSubId());
                    }
                }
            }
        };

        treeView.setAdapter(adapter);
        TreeSubBleDevice treeSubBleDevice = new TreeSubBleDevice(subBleManager.getSubBleDevices(bleDeviceInfo.getMacCode()));
        adapter.setRootNode(treeSubBleDevice.builTree());

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
                    bleDevice=device;
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
                   BleMLDevice bleDevice = ble.getBleDevice(subBleDevice.getMacCode());
                   if (bleDevice!=null&&bleDevice.isConnected()){
                       if (subBleDevice.getState()==1){
                           bytes=ByteUtils.hexStr2Bytes("FFFA0004"+subBleDevice.getSubId()+"1100");
                           subBleManager.updateSubDeviceState(0,subBleDevice.getMacCode(),subBleDevice.getSubId());
                       }else {
                           bytes=ByteUtils.hexStr2Bytes("FFFA0004"+subBleDevice.getSubId()+"1101");
                           subBleManager.updateSubDeviceState(1,subBleDevice.getMacCode(),subBleDevice.getSubId());
                       }
                       ble.writeByUuid(bleDevice, bytes, Ble.options().getUuidService(), Ble.options().getUuidWriteCha(), new BleWriteCallback<BleMLDevice>() {
                           @Override
                           public void onWriteSuccess(BleMLDevice device, BluetoothGattCharacteristic characteristic) {

                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       subBleDeviceAdapter.setNewInstance(subBleManager.getSubBleDevices(device.getBleAddress()));
                                   }
                               });
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
    protected void onDestroy() {
        super.onDestroy();
        if (ble!=null){
            bleDevice = ble.getBleDevice(bleDeviceInfo.getMacCode());
            ble.disconnect(bleDevice);
        }
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
                    boolean isSwitchState=MMKV.defaultMMKV().getBoolean("ALL_STATE"+bleDevice.getBleAddress(), true);
                    if (isSwitchState){
                        MMKV.defaultMMKV().putBoolean("ALL_STATE"+bleDevice.getBleAddress(), false);
                        imageView.setImageResource(R.mipmap.switch_close);
                        subBleManager.updateSubDeviceState(0,bleDeviceInfo.getMacCode(),"");
                        ble.writeByUuid(bleDevice,bytes,Ble.options().getUuidService(),Ble.options().getUuidWriteCha(), new BleWriteCallback<BleMLDevice>() {
                            @Override
                            public void onWriteSuccess(BleMLDevice device, BluetoothGattCharacteristic characteristic) {

                            }
                        });

                    }else {
                        subBleManager.updateSubDeviceState(1,bleDeviceInfo.getMacCode(),"");
                        MMKV.defaultMMKV().putBoolean("ALL_STATE"+bleDevice.getBleAddress(), true);
                        imageView.setImageResource(R.mipmap.switch_open_1);
                        bytes=ByteUtils.hexStr2Bytes("FFFA000400001201");
                        ble.writeByUuid(bleDevice,bytes,Ble.options().getUuidService(),Ble.options().getUuidWriteCha(), new BleWriteCallback<BleMLDevice>() {
                            @Override
                            public void onWriteSuccess(BleMLDevice device, BluetoothGattCharacteristic characteristic) {

                            }
                        });
                    }
                    subBleDeviceAdapter.setNewInstance(subBleManager.getSubBleDevices(bleDevice.getBleAddress()));
                }
                break;
        }
    }
}
