package com.mlink.bluetooth.gateway.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BluetoothDeviceStore {

    private final Map<String, BleMLDevice> mDeviceMap;

    public BluetoothDeviceStore() {
        mDeviceMap = new HashMap<>();
    }

    public void addDevice(BleMLDevice device) {
        if (device == null) {
            return;
        }
        if (mDeviceMap.containsKey(device.getBleAddress())) {

        } else {
            mDeviceMap.put(device.getBleAddress(), device);
        }
    }

    public void removeDevice(BleMLDevice device) {
        if (device == null) {
            return;
        }
        if (mDeviceMap.containsKey(device.getBleAddress())) {
            mDeviceMap.remove(device.getBleAddress());
        }
    }

    public void clear() {
        mDeviceMap.clear();
    }

    public Map<String, BleMLDevice> getDeviceMap() {
        return mDeviceMap;
    }

    public List<BleMLDevice> getDeviceList() {
        final List<BleMLDevice> methodResult = new ArrayList<>(mDeviceMap.values());

        Collections.sort(methodResult, new Comparator<BleMLDevice>() {

            @Override
            public int compare(final BleMLDevice arg0, final BleMLDevice arg1) {
                return arg0.getBleAddress().compareToIgnoreCase(arg1.getBleAddress());
            }
        });

        return methodResult;
    }

    @Override
    public String toString() {
        return "BluetoothLeDeviceStore{" +
                "DeviceList=" + getDeviceList() +
                '}';
    }


}
