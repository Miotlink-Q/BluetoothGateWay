package com.mlink.bluetooth.gateway.ui;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.ml.bluetooth.gateway.ble.Ble;
import com.ml.bluetooth.gateway.ble.BleLog;
import com.ml.bluetooth.gateway.ble.callback.BleConnectCallback;
import com.ml.bluetooth.gateway.ble.callback.BleNotifyCallback;
import com.ml.bluetooth.gateway.ble.callback.BleWriteCallback;
import com.ml.bluetooth.gateway.ble.utils.ByteUtils;
import com.mlink.bluetooth.gateway.R;
import com.mlink.bluetooth.gateway.adapter.SubBleDeviceAdapter;
import com.mlink.bluetooth.gateway.application.GateWayApplication;
import com.mlink.bluetooth.gateway.base.BaseActivity;
import com.mlink.bluetooth.gateway.bean.BleDeviceInfo;
import com.mlink.bluetooth.gateway.bean.BleMLDevice;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;
import com.mlink.bluetooth.gateway.bean.TreeSubBleDevice;
import com.mlink.bluetooth.gateway.db.SubBleManager;
import com.mlink.bluetooth.gateway.utils.Consts;
import com.mlink.bluetooth.gateway.view.RadarView;

import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;

public class SubBleDeviceAddActivity extends BaseActivity {

    public static BleMLDevice bleMLDevice = null;
    private Ble<BleMLDevice> ble = Ble.getInstance();
    private String subId = "";

    private MyThread myThread = null;

    SubBleManager subBleManager = null;

    private String level = "0";

    private SubBleDeviceAdapter subBleDeviceAdapter = null;


    private RadarView radarView = null;

    private RecyclerView recyclerView = null;

    private ImageView startIv = null;

    private TextView connectTv = null;

    private boolean isConnect = false;

    private TreeView treeView;
    private BaseTreeAdapter adapter = null;

    @Override
    public void initView() throws Exception {
        startIv = findViewById(R.id.start_iv);
        connectTv = findViewById(R.id.device_connect_tv);
        treeView = findViewById(R.id.treeView);
        radarView = findViewById(R.id.radar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnect) {
                    Toast.makeText(mContext, "连接已断开", Toast.LENGTH_LONG).show();
                }
                radarView.start();
                startIv.setImageResource(R.mipmap.start_2);
                if (myThread != null) {
                    myThread.interrupt();
                    myThread = null;
                }
                myThread = new MyThread();
                myThread.setRunning(true);
                subId = "FFFF";
                myThread.start();
            }
        });

        subBleManager = SubBleManager.getInstance(GateWayApplication.getInstance());
        subBleDeviceAdapter = new SubBleDeviceAdapter();

        recyclerView.setAdapter(subBleDeviceAdapter);
        List<SubBleDevice> subBleDevices = subBleManager.getSubBleDevices(bleMLDevice.getBleAddress());
        adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.item_node_sub_device) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {

                if (data instanceof SubBleDevice) {
                    SubBleDevice subBleDevice = (SubBleDevice) data;
                    if (!TextUtils.isEmpty(subBleDevice.getSubId())
                            &&subBleDevice.getSubId().length()>2){
                        if (subBleDevice.getSubId().startsWith("FF")
                                ||subBleDevice.getSubId().startsWith("00")){
                            viewHolder.mTextView.setText(subBleDevice.getSubId().substring(2,subBleDevice.getSubId().length()));
                        }else {
                            viewHolder.mTextView.setText(subBleDevice.getSubId());
                        }
                    }else {
                        viewHolder.mTextView.setText(subBleDevice.getSubId());
                    }
                }
            }
        };


        treeView.setAdapter(adapter);
        TreeSubBleDevice treeSubBleDevice = new TreeSubBleDevice(subBleDevices);
//            adapter.setRootNode(Consts.getTreeNode(bleMLDevice.getBleAddress()));
        adapter.setRootNode(treeSubBleDevice.builTree());
        if (subBleDevices != null && subBleDevices.size() > 0) {
            subBleDeviceAdapter.setNewInstance(subBleDevices);

        }
        treeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TreeNode mCurrentNode = adapter.getNode(position);
                if (mCurrentNode.getData() instanceof SubBleDevice) {
                    SubBleDevice subBleDevice = (SubBleDevice) mCurrentNode.getData();
                    if (subBleDevice != null) {
                        if (myThread != null) {
                            myThread.interrupt();
                            myThread = null;
                        }
                        myThread = new MyThread();
                        if (TextUtils.isEmpty(subBleDevice.getSubId()) || subBleDevice.getSubId().equals("Root")) {
                            subId = "FFFF";
                            level = "FFFF";
                        } else {
                            subId = subBleDevice.getSubId();
                            level = subId;
                        }

                        if (!isConnect) {
                            Toast.makeText(mContext, "连接已断开", Toast.LENGTH_LONG).show();
                        }

                        radarView.start();
                        myThread.start();

                    }
                }
            }
        });
        subBleDeviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SubBleDevice subBleDevice = (SubBleDevice) adapter.getItem(position);
                if (subBleDevice!=null){
                    if (!isConnect) {
                        Toast.makeText(mContext, "连接已断开", Toast.LENGTH_LONG).show();
                        return;
                    }
                    byte [] bytes=null;
                    BleMLDevice bleDevice = ble.getBleDevice(subBleDevice.getMacCode());
                    if (bleDevice!=null&&bleDevice.isConnected()){
                        if (subBleDevice.getState()==1){
                            bytes=ByteUtils.hexStr2Bytes("FFFA0004"+subBleDevice.getSubId()+"1100");
                            subBleManager.updateSubDeviceState(0,subBleDevice.getMacCode(),subBleDevice.getSubId());
                        }else {
                            bytes=ByteUtils.hexStr2Bytes("FFFA0004"+subBleDevice.getSubId()+"1101");
                            subBleManager.updateSubDeviceState(1,subBleDevice.getMacCode(),subBleDevice.getSubId());
                        }
                        ble.writeByUuid(bleDevice, bytes, Ble.options().getUuidService(), Ble.options().getUuidWriteCha(), new BleWriteCallback<BleMLDevice>() {
                            @Override
                            public void onWriteSuccess(BleMLDevice device, BluetoothGattCharacteristic characteristic) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        subBleDeviceAdapter.setNewInstance(subBleManager.getSubBleDevices(device.getBleAddress()));
                                    }
                                });
                            }
                        });

                    }

                }
            }
        });
        myThread = new MyThread();

        List<BleMLDevice> connectedDevices = ble.getConnectedDevices();
        if (connectedDevices != null && connectedDevices.size() > 0) {
            for (BleMLDevice ble : connectedDevices) {
                if (bleMLDevice.getBleAddress().equals(ble.getBleAddress())) {
                    isConnect = true;
                    connectTv.setText("(已连接)");
                    break;
                }
            }
        }
        if (isConnect) {
            ble.enableNotify(bleMLDevice, true, bleMLDeviceBleNotifyCallback);
        } else {
            ble.connect(bleMLDevice, new BleConnectCallback<BleMLDevice>() {
                @Override
                public void onConnectionChanged(BleMLDevice device) {
                    if (device.isConnected()) {
                        isConnect = true;
                        connectTv.setText("(已连接)");
                    } else if (device.isConnecting()) {
                        isConnect = false;
                        connectTv.setText("(连接中)");
                    } else if (device.isDisconnected()) {
                        connectTv.setText("(连接已断开)");
                        isConnect = false;
                    } else {
                        connectTv.setText("(连接失败)");
                    }
                }

                @Override
                public void onServicesDiscovered(BleMLDevice device, BluetoothGatt gatt) {
                    super.onServicesDiscovered(device, gatt);

                }

                @Override
                public void onReady(BleMLDevice device) {
                    super.onReady(device);
                    ble.enableNotify(device, true, bleMLDeviceBleNotifyCallback);
                }
            });

        }

    }

    @Override
    public int getContentView() {
        return R.layout.activity_add_sub_device;
    }

    private class ViewHolder {
        TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.textView);
        }
    }

    class MyThread extends Thread {

        boolean isRunning = false;

        public void setRunning(boolean running) {
            isRunning = running;
        }

        @Override
        public void run() {
            super.run();
            try {
                if (isRunning) {
                    Thread.sleep(2000);
                }
                if (!TextUtils.isEmpty(subId)) {
                    String sub = "FFFA0004" + subId + "0100";
                    BleLog.e("gateway", sub);
                    byte[] bytes = ByteUtils.hexStr2Bytes(sub);
                    ble.writeByUuid(bleMLDevice, bytes, Ble.options().getUuidService(), Ble.options().getUuidWriteCha(), bleWriteCallback);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    BleWriteCallback bleWriteCallback = new BleWriteCallback() {
        @Override
        public void onWriteSuccess(Object device, BluetoothGattCharacteristic characteristic) {

        }
    };

    BleNotifyCallback<BleMLDevice> bleMLDeviceBleNotifyCallback = new BleNotifyCallback<BleMLDevice>() {
        @Override
        public void onChanged(BleMLDevice device, BluetoothGattCharacteristic characteristic) {
            if (characteristic != null) {
                UUID uuid = characteristic.getUuid();
                if (uuid.equals(Ble.options().getUuidReadCha())) {
                    byte[] value = characteristic.getValue();
                    String s = ByteUtils.bytes2HexStr(value);
                    BleLog.e("error", s);
                    String[] strings = ByteUtils.bytes2HexStrings(value);
                    if (!TextUtils.isEmpty(s) && s.startsWith("fffa") && s.endsWith("03")) {
                        BleLog.e("value", strings.toString());
                        if (strings != null && strings.length > 0) {
                            SubBleDevice subBleDevice = new SubBleDevice();
                            subBleDevice.setSubId(strings[4] + strings[5]);
                            subBleDevice.setMacCode(device.getBleAddress());
                            subBleDevice.setLevel(level);
                            subBleManager.addSubBleDevice(subBleDevice);
                            BleDeviceInfo bleDeviceInfo = new BleDeviceInfo();
                            bleDeviceInfo.setId(device.getBleAddress().replaceAll(":", ""));
                            bleDeviceInfo.setState(1);
                            bleDeviceInfo.setMacCode(device.getBleAddress());
                            subBleManager.addDevice(bleDeviceInfo);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<SubBleDevice> subBleDevices = subBleManager.getSubBleDevices(bleMLDevice.getBleAddress());
                                    if (subBleDevices != null) {
                                        BleLog.e("subBleDevices", subBleDevices.toString());
                                        subBleDeviceAdapter.setNewInstance(subBleDevices);

                                        TreeSubBleDevice treeSubBleDevice = new TreeSubBleDevice(subBleDevices);
//            adapter.setRootNode(Consts.getTreeNode(bleMLDevice.getBleAddress()));
                                        adapter.setRootNode(treeSubBleDevice.builTree());
                                    }


                                }
                            });
                        }
                    }
                }
            }
        }
    };
}
