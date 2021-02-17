package com.mlink.bluetooth.gateway.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScanDeviceAdapter extends BaseQuickAdapter<BleMLDevice, BaseViewHolder> {

    public ScanDeviceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, BleMLDevice bleMLDevice) {

        viewHolder.setText(R.id.bluetooth_tv,bleMLDevice.getBleName()+"("+bleMLDevice.getBleAddress()+")");
    }
}
