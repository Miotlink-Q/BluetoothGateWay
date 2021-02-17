package com.mlink.bluetooth.gateway.db;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.data.d.db.DBUtils.DataManager;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;

import java.util.List;

public
class SubBleManager extends DataManager {

    private static SubBleManager instance=null;

    public static SubBleManager getInstance(Context mContext) {
        if (instance==null){
            instance=new SubBleManager(mContext);
        }
        return instance;
    }

    public SubBleManager(Context context) {
        super(context);
    }

    public void addSubBleDevice(SubBleDevice subBleDevice) {
        try {
            Selector selector= Selector.from(SubBleDevice.class).
                    where("macCode", "=", subBleDevice.getMacCode()).and("subId", "=",
                    subBleDevice.getSubId());
            SubBleDevice subBleDevice1 = dbUtils.findFirst(selector);
            if (subBleDevice1==null){
                dbUtils.save(subBleDevice);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<SubBleDevice> getSubBleDevices(String macCode){
        Selector selector= Selector.from(SubBleDevice.class).where("macCode", "=", macCode);
        try {
            return dbUtils.findAll(selector);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;

    }
}
