package com.mlink.bluetooth.gateway.ui;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.BleLog;
import com.ml.bluetooth.gateway.ble.callback.BleStatusCallback;
import com.ml.bluetooth.gateway.ble.utils.Utils;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;

import androidx.appcompat.app.AlertDialog;

public
class HomeActivity extends BaseActivity implements View.OnClickListener {


    private TextView textView;

    @Override
    public void initView() throws Exception {
        textView=findViewById(R.id.add_bluetooth);
        textView.setOnClickListener(this);
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


}
