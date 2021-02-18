package com.mlink.bluetooth.gateway.db;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.data.d.db.DBUtils.DataManager;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;

import java.util.ArrayList;
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
        return new ArrayList<>();
    }

    public void updateSubDeviceState(int state,String macCode,String subId){
        try {
            SubBleDevice subBleDevice=new SubBleDevice();
            subBleDevice.setState(state);
            subBleDevice.setMacCode(macCode);
            if (TextUtils.isEmpty(subId)){

                WhereBuilder whereBuilder= WhereBuilder.b("macCode","=",macCode);
                dbUtils.update(subBleDevice,whereBuilder,"state");

            }else {
                WhereBuilder whereBuilder= WhereBuilder.b("macCode","=",macCode).and("subId","=",subId);
                dbUtils.update(subBleDevice,whereBuilder,"state");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDevice(BleDeviceInfo bleDeviceInfo){
        try {
            dbUtils.saveOrUpdate(bleDeviceInfo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteDevice(String id){

        try {
            WhereBuilder whereBuilder=WhereBuilder.b("macCode","=",id);
            dbUtils.delete(BleDeviceInfo.class,whereBuilder);
            WhereBuilder whereBuilder1=WhereBuilder.b("macCode","=",id);
            dbUtils.delete(SubBleDevice.class,whereBuilder1);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public List<BleDeviceInfo> getBleDeviceInfos(){
        try {
            return dbUtils.findAll(BleDeviceInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public void updateBleDeviceInfo(String macCode,int state){
        try {
            BleDeviceInfo bleDeviceInfo =new BleDeviceInfo();
            bleDeviceInfo.setState(state);
            WhereBuilder whereBuilder= WhereBuilder.b("macCode","=",macCode);
            dbUtils.update(bleDeviceInfo,whereBuilder,"state");

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public BleDeviceInfo  getBleDeviceInfo(String id){
        Selector selector= Selector.from(BleDeviceInfo.class).where("id", "=", id);
        try {
            return dbUtils.findFirst(selector);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAll(){
        try {
            dbUtils.execQuery("update ble_device set state = "+0);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


}
