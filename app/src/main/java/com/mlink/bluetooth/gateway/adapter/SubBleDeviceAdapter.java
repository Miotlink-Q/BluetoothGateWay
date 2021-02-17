package com.mlink.bluetooth.gateway.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;

import org.jetbrains.annotations.NotNull;

public class SubBleDeviceAdapter extends BaseQuickAdapter<SubBleDevice, BaseViewHolder> {

    public SubBleDeviceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SubBleDevice subBleDevice) {

    }
}
