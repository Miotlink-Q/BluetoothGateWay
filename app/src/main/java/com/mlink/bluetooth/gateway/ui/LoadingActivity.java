package com.mlink.bluetooth.gateway.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.ml.bluetooth.gateway.ble.Ble;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.application.GateWayApplication;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;
import com.mlink.bluetooth.gateway.db.SubBleManager;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;


public class LoadingActivity extends BaseActivity {

    private Ble<BleMLDevice> ble=Ble.getInstance();
    @Override
    public void initView() throws Exception {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SubBleManager.getInstance(GateWayApplication.getInstance()).updateAll();

                PermissionX.init(LoadingActivity.this).permissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE).request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted){
                            if (ble!=null){
                                if (ble.isBleEnable()){

                                }else {
                                    ble.turnOnBlueToothNo();
                                }

                                mContext.startActivity(new Intent(mContext,HomeActivity.class));
                                finish();
                            }

                        }
                    }
                });
            }
        },3000);


    }

    @Override
    public int getContentView() {
        return R.layout.activity_loading;
    }


}
