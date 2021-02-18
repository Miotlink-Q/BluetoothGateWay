package com.mlink.bluetooth.gateway.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;

import org.jetbrains.annotations.NotNull;

public
class BleDeviceAdapter extends BaseQuickAdapter<BleDeviceInfo, BaseViewHolder> {

    public BleDeviceAdapter() {
        super(R.layout.item_sub_device);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BleDeviceInfo bleDeviceInfo) {
        baseViewHolder.setText(R.id.sub_device_name_tv,bleDeviceInfo.getId());
    }
}
