package com.mlink.bluetooth.gateway.adapter;

import android.view.View;
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
        ImageView liv=baseViewHolder.itemView.findViewById(R.id.sub_device_l_iv);
        if (subBleDevice.getLevel()==1){
            liv.setVisibility(View.VISIBLE);
            liv.setImageResource(R.mipmap.leal1);
        }else if (subBleDevice.getLevel()==2){
            liv.setVisibility(View.VISIBLE);
            liv.setImageResource(R.mipmap.leal2);
        }else if (subBleDevice.getLevel()==3){
            liv.setVisibility(View.VISIBLE);
            liv.setImageResource(R.mipmap.leal3);
        }

        if (subBleDevice.getState()==1){
            imageView.setImageResource(R.mipmap.led_2);
        }else {
            imageView.setImageResource(R.mipmap.led_1);
        }
    }
}
