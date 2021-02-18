package com.mlink.bluetooth.gateway.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;

import org.jetbrains.annotations.NotNull;

public
class BleDeviceAdapter extends BaseQuickAdapter<BleDeviceInfo, BaseViewHolder> {

    public BleDeviceAdapter() {
        super(R.layout.item_add_device);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BleDeviceInfo bleDeviceInfo) {
        baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getId());
        ImageView imageView=baseViewHolder.itemView.findViewById(R.id.device_iv);
        if (bleDeviceInfo.getState()==2){
            baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getId()+" "+"(已连接)");
            imageView.setImageResource(R.mipmap.bluetooth_device_1);
        }else if(bleDeviceInfo.getState()==1) {
            baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getId()+" "+"(未连接)");
            imageView.setImageResource(R.mipmap.bluetooth_device_2);
        }else {
            baseViewHolder.setText(R.id.device_name,bleDeviceInfo.getId()+" "+"(未扫描到设备)");
            imageView.setImageResource(R.mipmap.bluetooth_device_2);
        }
    }
}
