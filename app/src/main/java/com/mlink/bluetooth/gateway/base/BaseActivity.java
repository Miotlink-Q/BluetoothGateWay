package com.mlink.bluetooth.gateway.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(getContentView());
            mContext=this;
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void initView()throws Exception;

    public abstract int getContentView();
}
