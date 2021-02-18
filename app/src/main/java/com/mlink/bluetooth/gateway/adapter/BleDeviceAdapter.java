package com.mlink.bluetooth.gateway.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ml.bluetooth.gateway.ble.Ble;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.application.GateWayApplication;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;
import com.mlink.bluetooth.gateway.db.SubBleManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public
class BleDeviceAdapter extends BaseQuickAdapter<BleDeviceInfo, BaseViewHolder> {

    Ble<BleMLDevice> ble=Ble.getInstance();

    public BleDeviceAdapter() {
        super(R.layout.item_add_device);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BleDeviceInfo bleDeviceInfo) {
        baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getId());
        ImageView imageView=baseViewHolder.itemView.findViewById(R.id.device_iv);


        if (bleDeviceInfo.getState()==1){
            baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getMacCode()+" "+"(已发现)");
            imageView.setImageResource(R.mipmap.bluetooth_device_2);
        }else if (bleDeviceInfo.getState()==2){
            baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getMacCode()+" "+"(已连接)");
            imageView.setImageResource(R.mipmap.bluetooth_device_1);
        }else {
            baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getMacCode()+" "+"(未连接)");
            imageView.setImageResource(R.mipmap.bluetooth_device_2);
        }

    }
}
