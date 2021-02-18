package com.mlink.bluetooth.gateway.ui;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.BleLog;
import com.ml.bluetooth.gateway.ble.callback.BleScanCallback;
import com.ml.bluetooth.gateway.ble.callback.BleStatusCallback;
import com.ml.bluetooth.gateway.ble.utils.Utils;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.adapter.BleDeviceAdapter;
import com.mlink.bluetooth.gateway.application.GateWayApplication;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;
import com.mlink.bluetooth.gateway.bean.BluetoothDeviceStore;
import com.mlink.bluetooth.gateway.db.SubBleManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public
class HomeActivity extends BaseActivity implements View.OnClickListener {


    private TextView textView;

    private RecyclerView recyclerView;


    private BluetoothDeviceStore bluetoothDeviceStore=new BluetoothDeviceStore();
    private List<BleDeviceInfo> bleDeviceInfos=null;

    private BleDeviceAdapter bleDeviceAdapter=null;

    private Ble<BleMLDevice> ble=Ble.getInstance();

    @Override
    public void initView() throws Exception {
        textView=findViewById(R.id.add_bluetooth);
        recyclerView=findViewById(R.id.recyclerView);
        textView.setOnClickListener(this);
        bleDeviceInfos= SubBleManager.getInstance(GateWayApplication.getInstance()).getBleDeviceInfos();
        bleDeviceAdapter=new BleDeviceAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.getItemAnimator().setChangeDuration(300);
        recyclerView.getItemAnimator().setMoveDuration(300);
        recyclerView.setAdapter(bleDeviceAdapter);

        bleDeviceAdapter.setNewInstance(bleDeviceInfos);
        bleDeviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                BleDeviceInfo bleDeviceInfo=(BleDeviceInfo) adapter.getItem(position);
                DeviceControllerActivity.bleDeviceInfo=bleDeviceInfo;
                mContext.startActivity(new Intent(mContext,DeviceControllerActivity.class));
            }
        });
        bleDeviceAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                BleDeviceInfo bleDeviceInfo=(BleDeviceInfo) adapter.getItem(position);

                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setMessage("是否删除该设备")
                        .setPositiveButton("确定", (dialog, which) -> {

                            SubBleManager.getInstance(GateWayApplication.getInstance()).deleteDevice(bleDeviceInfo.getMacCode());
                            bleDeviceAdapter.setNewInstance(SubBleManager.getInstance(GateWayApplication.getInstance()).getBleDeviceInfos());
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ble!=null){
            if(ble.isBleEnable()){
                ble.startScan(bleMLDeviceBleScanCallback);
                List<BleMLDevice> connectedDevices = ble.getConnectedDevices();
                if (connectedDevices!=null&&connectedDevices.size()>0){
                    for (BleMLDevice bleMLDevice:connectedDevices){
                        SubBleManager.getInstance(GateWayApplication.getInstance()).updateBleDeviceInfo(bleMLDevice.getBleAddress(),2);
                    }
                }
            }

        }
        bleDeviceInfos= SubBleManager.getInstance(GateWayApplication.getInstance()).getBleDeviceInfos();
        bleDeviceAdapter.setNewInstance(bleDeviceInfos);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (ble!=null){
            ble.stopScan();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_bluetooth:
                mContext.startActivity(new Intent(mContext,ScanDeviceActivity.class));
                break;
        }
    }


    BleScanCallback<BleMLDevice> bleMLDeviceBleScanCallback=new BleScanCallback<BleMLDevice>() {
        @Override
        public void onLeScan(BleMLDevice device, int rssi, byte[] scanRecord) {
            if (!TextUtils.isEmpty(device.getBleName())&&device.getBleName().contains("Simple")){

                if (!bluetoothDeviceStore.getDeviceMap().containsKey(device.getBleAddress())){
                    bluetoothDeviceStore.addDevice(device);
                    SubBleManager.getInstance(GateWayApplication.getInstance()).updateBleDeviceInfo(device.getBleAddress(),1);
                    bleDeviceInfos= SubBleManager.getInstance(GateWayApplication.getInstance()).getBleDeviceInfos();
                    bleDeviceAdapter.setNewInstance(bleDeviceInfos);
                }
            }


        }
    };


}
