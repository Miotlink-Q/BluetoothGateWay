package com.mlink.bluetooth.gateway.ui;

import android.Manifest;
import android.content.Intent;
import android.widget.Toast;

import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;

public class LoadingActivity extends BaseActivity {
    @Override
    public void initView() throws Exception {

        PermissionX.init(this).permissions(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if (allGranted){
                    mContext.startActivity(new Intent(mContext,HomeActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_loading;
    }


}
