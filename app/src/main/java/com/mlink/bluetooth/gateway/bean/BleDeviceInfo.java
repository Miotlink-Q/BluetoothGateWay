package com.mlink.bluetooth.gateway.bean;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "ble_device")
public class BleDeviceInfo {

    private String id="";

    private int state=0;

    private String name="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(int state) {
        this.state = state;
    }
}
