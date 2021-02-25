package com.mlink.bluetooth.gateway.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "sub_ble_table")
public class SubBleDevice {

    @Id
    private int  id=0;

    private String macCode="";

    /**
     * 子设备ID
     */
    private String subId="";

    private String level="0";

    /**
     * 子设备名称
     */
    private String name="";

    /**
     * 子设备在线状态
     */
    private int onlineState=0;

    /**
     * 子设备开关状态
     */
    private int state=1;

    private int nodeType=0;

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(int onlineState) {
        this.onlineState = onlineState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMacCode() {
        return macCode;
    }

    public void setMacCode(String macCode) {
        this.macCode = macCode;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getNodeType() {
        return nodeType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SubBleDevice{" +
                "id=" + id +
                ", macCode='" + macCode + '\'' +
                ", subId='" + subId + '\'' +
                ", name='" + name + '\'' +
                ", onlineState=" + onlineState +
                ", state=" + state +
                '}';
    }
}
