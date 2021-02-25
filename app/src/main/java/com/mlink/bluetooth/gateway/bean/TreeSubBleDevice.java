package com.mlink.bluetooth.gateway.bean;

import android.view.Menu;

import com.mlink.bluetooth.gateway.application.GateWayApplication;
import com.mlink.bluetooth.gateway.db.SubBleManager;

import java.util.ArrayList;
import java.util.List;

import de.blox.treeview.TreeNode;

public class TreeSubBleDevice {

    private List<SubBleDevice> subBleDevices = new ArrayList<>();


    public TreeSubBleDevice(List<SubBleDevice> subBleDevices) {
        this.subBleDevices = subBleDevices;


    }

    /**
     * 建立树结果
     */
    public TreeNode builTree() {

        SubBleDevice subBleDevice=new SubBleDevice();
        subBleDevice.setLevel("FFFF");
        subBleDevice.setSubId("ROOT");
        TreeNode treeNode=new TreeNode(subBleDevice);
        if (subBleDevices!=null&&subBleDevices.size()>0){
            for (SubBleDevice subBleDevice1:subBleDevices){
                if (subBleDevice1.getLevel().equals("FFFF")){
                    TreeNode treeNode1 = buildChilTree(subBleDevice1);
                    if (treeNode1!=null){
                        treeNode.addChild(treeNode1);
                    }
                }
            }
        }

        return treeNode;
    }

    private TreeNode buildChilTree(SubBleDevice rootSubBleDevice) {
        TreeNode treeNode=new TreeNode(rootSubBleDevice);
        for (SubBleDevice menuNode : subBleDevices) {
            if (menuNode.getLevel().equals(rootSubBleDevice.getSubId())) {
                treeNode.addChild(buildChilTree(menuNode));
            }
        }

        return treeNode;
    }
}
