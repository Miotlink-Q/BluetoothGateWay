package com.miot.android.orm;

/**
 * 
 * 
 * 
 * @author Administrator
 * 
 */
public class Model extends Object {


	private String mCode="";
	
	private String modelId="";
	
	private String modelName="";
	
	private String firstConfigUrl="";
	
	private String categoryId="";
	
	private String plugin="";

	private long time=0L; //每次请求更新插件的时间保存到数据库

//	apk,cloudH5 ,nativeH5 本地html
	private String pluginMode="";

	private String pluginRes="";//插件主页面图片

	public String getPluginRes() {
		return pluginRes;
	}

	public void setPluginRes(String pluginRes) {
		this.pluginRes = pluginRes;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getFirstConfigUrl() {
		return firstConfigUrl;
	}

	public void setFirstConfigUrl(String firstConfigUrl) {
		this.firstConfigUrl = firstConfigUrl;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getPluginMode() {
		return pluginMode;
	}

	public void setPluginMode(String pluginMode) {
		this.pluginMode = pluginMode;
	}

	@Override
	public String toString() {
		return "Model{" +
				"mCode='" + mCode + '\'' +
				", modelId='" + modelId + '\'' +
				", modelName='" + modelName + '\'' +
				", firstConfigUrl='" + firstConfigUrl + '\'' +
				", categoryId='" + categoryId + '\'' +
				", plugin='" + plugin + '\'' +
				", time=" + time +
				", pluginMode='" + pluginMode + '\'' +
				", pluginRes='" + pluginRes + '\'' +
				", time='" + time + '\'' +
				'}';
	}
}
