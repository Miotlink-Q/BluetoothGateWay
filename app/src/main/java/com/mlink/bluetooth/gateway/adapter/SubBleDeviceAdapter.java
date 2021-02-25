package com.mlink.bluetooth.gateway.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView liv=baseViewHolder.itemView.findViewById(R.id.sub_device_l_tv);
        if (!TextUtils.isEmpty(subBleDevice.getLevel())
                &&subBleDevice.getLevel().length()>2){
            if (subBleDevice.getLevel().startsWith("FF")
                    ||subBleDevice.getLevel().startsWith("00")){
                liv.setText(subBleDevice.getLevel().substring(2,subBleDevice.getLevel().length()));
            }
        }else {
            liv.setText(subBleDevice.getLevel());
        }
        liv.setVisibility(View.VISIBLE);
        if (subBleDevice.getNodeType()==1){
            liv.setBackgroundResource(R.drawable.device_shape_1);
        }
        if (subBleDevice.getState()==1){
            imageView.setImageResource(R.mipmap.led_2);
        }else {
            imageView.setImageResource(R.mipmap.led_1);
        }
    }
}
