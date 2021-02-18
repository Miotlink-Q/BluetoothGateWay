package com.mlink.bluetooth.gateway.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;

import org.jetbrains.annotations.NotNull;

public class SubBleDeviceAdapter extends BaseQuickAdapter<SubBleDevice, BaseViewHolder> {

    public SubBleDeviceAdapter() {
        super(R.layout.item_sub_device);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SubBleDevice subBleDevice) {

        baseViewHolder.setText(R.id.sub_device_name_tv,subBleDevice.getSubId());
        ImageView imageView=baseViewHolder.itemView.findViewById(R.id.sub_device_state_iv);
        if (subBleDevice.getState()==1){
            imageView.setImageResource(R.mipmap.led_2);
        }else {
            imageView.setImageResource(R.mipmap.led_1);
        }
    }
}
