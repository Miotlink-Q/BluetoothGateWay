package com.miot.android.data.d.db.DBUtils.entity;

import android.content.ContentValues;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by xdf on 2016/7/4.
 */
public class ReplaceDao {

    public static String REPLACE_CODE="code";
    public static String REPLACE_VALUES="replace";
    public static String CONTAINERDATA="containerData";
    public static String DATA="data";

    private String container;
    private String seq;
    private ContainerData containerData;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public ContainerData getContainerData() {
        return containerData;
    }

    public void setContainerData(ContainerData containerData) {
        this.containerData = containerData;
    }

    public class ContainerData{
        private String code;
        private Data data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public class Data{
        private String tableName;
        private Object data;

        public String getTableName(){
            return tableName;
        }

        public void setTableName(String tableName){
            this.tableName = tableName;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }


    public static boolean getIsReplaceData(String json){
        ContentValues value=new ContentValues();
        boolean isReplace=false;
        try {
            JSONObject obj=new JSONObject(json);
            String code=obj.getJSONObject(CONTAINERDATA).getString(REPLACE_CODE);
            if(code.equals(REPLACE_VALUES)){
                isReplace=true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isReplace=false;
        }
        return isReplace;
    }

    public static ReplaceDao parseJson(String json){
        if(!TextUtils.isEmpty(json)){
            return new Gson().fromJson(json,ReplaceDao.class);
        }
        return null;
    }

    public static ContentValues getReplaceData(String json){
        boolean isReplace=getIsReplaceData(json);
        ContentValues value=null;
        if(isReplace){
            try {
                JSONObject obj=new JSONObject(json).getJSONObject(CONTAINERDATA).getJSONObject(DATA).getJSONObject(DATA);
                Iterator it=obj.keys();
                value=new ContentValues();
                while(it.hasNext()){

                    String key= (String) it.next();
                    String values=obj.getString(key);
                    value.put(key,values);
                }
                return value;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return value;
        }
        return value;
    }
}
