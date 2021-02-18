package com.mlink.bluetooth.gateway.ui;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.callback.BleScanCallback;
import com.ml.bluetooth.gateway.ble.callback.BleStatusCallback;
import com.ml.bluetooth.gateway.ble.utils.Utils;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.adapter.ScanDeviceAdapter;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;
import com.mlink.bluetooth.gateway.bean.BluetoothDeviceStore;
import com.mlink.bluetooth.gateway.view.RadarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public
class ScanDeviceActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ScanDeviceAdapter scanDeviceAdapter=null;
    private BluetoothDeviceStore bluetoothDeviceStore=new BluetoothDeviceStore();

    private TextView textView;
    private Ble<BleMLDevice> ble = Ble.getInstance();
    private RadarView radarView=null;
    @Override
    public void initView() throws Exception {

        radarView=findViewById(R.id.radar);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        scanDeviceAdapter=new ScanDeviceAdapter(R.layout.item_scan_device);
        recyclerView.getItemAnimator().setChangeDuration(300);
        recyclerView.getItemAnimator().setMoveDuration(300);
        recyclerView.setAdapter(scanDeviceAdapter);
        initBleStatus();
        bluetoothDeviceStore.clear();

        scanDeviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                BleMLDevice item = (BleMLDevice)adapter.getItem(position);
                AddSubDeviceActivity.bleMLDevice=item;
                mContext.startActivity(new Intent(mContext,AddSubDeviceActivity.class));
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_sacn_device;
    }

    private void initBleStatus() {
        checkGpsStatus();
        ble.setBleStatusCallback(new BleStatusCallback() {
            @Override
            public void onBluetoothStatusChanged(boolean isOn) {


                if (isOn){
                    checkGpsStatus();
                }else {
                    if (ble.isScanning()) {
                        ble.stopScan();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkGpsStatus();
        if (radarView!=null){
            radarView.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ble.isScanning()) {
            ble.stopScan();
        }
        if (radarView!=null){
            radarView.stop();
        }
    }

    private void checkGpsStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Utils.isGpsOpen(mContext)){
            new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("为了更精确的扫描到Bluetooth LE设备,请打开GPS定位")
                    .setPositiveButton("确定", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,1001);
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();
        }else {
            if (ble!=null){
                ble.startScan(scanCallback);
            }

        }
    }

    BleScanCallback<BleMLDevice> scanCallback=new BleScanCallback<BleMLDevice>() {
        @Override
        public void onLeScan(BleMLDevice device, int rssi, byte[] scanRecord) {
            if (!TextUtils.isEmpty(device.getBleName())&&device.getBleName().startsWith("Simple")){
                if (!bluetoothDeviceStore.getDeviceMap().containsKey(device.getBleAddress())){
                    bluetoothDeviceStore.addDevice(device);
                    scanDeviceAdapter.setNewInstance(bluetoothDeviceStore.getDeviceList());
                }
            }


        }
    };
}
