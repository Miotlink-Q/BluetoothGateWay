package com.mlink.bluetooth.gateway.db;

import android.content.Context;

import com.lidroid.xutils.exception.DbException;
import com.miot.android.data.d.db.DBUtils.DataManager;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;

import java.util.List;

public
class SubBleManager extends DataManager {

    public SubBleManager(Context context) {
        super(context);
    }

    public void addSubBleDevice(SubBleDevice subBleDevice) {
        try {
            dbUtils.saveOrUpdate(subBleDevice);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<SubBleDevice> getSubBleDevices(String macCode){
       // Selector selector=Selector.from(SubBleDevice.class).where("macCode", "=", macCode);
       // return dbUtils.findAll("",SubBleDevice.class);
        return null;
    }
}
