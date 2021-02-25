package com.mlink.bluetooth.gateway.utils;

import com.mlink.bluetooth.gateway.application.GateWayApplication;
import com.mlink.bluetooth.gateway.bean.SubBleDevice;
import com.mlink.bluetooth.gateway.db.SubBleManager;

import java.util.List;

import de.blox.treeview.TreeNode;

public class Consts {

    public static TreeNode getTreeNode(String macCode){
        SubBleDevice subBleDevice=new SubBleDevice();
        subBleDevice.setLevel("00");
        TreeNode treeNode=new TreeNode(subBleDevice);
        List<SubBleDevice> subBleDevices = SubBleManager.getInstance(GateWayApplication.getInstance()).getSubBleDevices(macCode);
        if (subBleDevices!=null&&subBleDevices.size()>0){
            for (SubBleDevice subBleDevice1:subBleDevices){
                if (subBleDevice1.getLevel().equals("0")){
                    TreeNode treeNodeRoot=new TreeNode(subBleDevice1);
                    treeNode.addChild(treeNodeRoot);
                }
            }
        }
        return treeNode;
    }
}
