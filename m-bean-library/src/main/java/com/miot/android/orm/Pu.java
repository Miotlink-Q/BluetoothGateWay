package com.miot.android.orm;

public class Pu extends Object {
	private String maccode = "";
	public final static int STATE_UNKNOWN = 0;
	public final static int SHARE_TRUE = 1;
	public final static int SHARE_FALSE = 2;
	private String longitude="";

	private String localIp="";
	
	private String latitude="";

	private int state = STATE_UNKNOWN;

	private int kindId;

	private int modelId;

	private String roomId;

	private long puIp = 1l;

	private int share = 0;

	private String location = "";

	private String mode = "";

	private String custom;

	private String comMode="";

	private String ownerCuId="";

	public String getOwnerCuId() {
		return ownerCuId;
	}

	public void setOwnerCuId(String ownerCuId) {
		this.ownerCuId = ownerCuId;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	// private String

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getPuIp() {
		return puIp;
	}

	public void setPuIp(long puIp) {
		this.puIp = puIp;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMaccode() {
		return maccode;
	}

	public void setMaccode(String maccode) {
		this.maccode = maccode;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getComMode() {
		return comMode;
	}

	public void setComMode(String comMode) {
		this.comMode = comMode;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}
}
