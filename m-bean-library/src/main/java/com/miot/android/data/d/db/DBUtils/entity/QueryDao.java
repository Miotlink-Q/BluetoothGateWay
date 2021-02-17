package com.miot.android.data.d.db.DBUtils.entity;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xdf on 2016/6/30.
 */
public class QueryDao {
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
        private QueryData data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public QueryData getData() {
            return data;
        }

        public void setData(QueryData data) {
            this.data = data;
        }
    }

    public class QueryData{
        private String resultCode;
        private String resultMsg;
        private List<Map<String,Object>> dataList;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public List<Map<String,Object>> getDataList() {
            return dataList;
        }

        public void setDataList(List<Map<String,Object>> dataList) {
            this.dataList = dataList;
        }
    }

//    "data":{
//        "resultCode":"1",
//                "resultMsg":"success",
//        “filedList”:[“id”,”name”,”type”],
//        "dataList":[[“1”,”A”,”T”],[“2”,”B”,”T1”]]
//    }

    public static String getQueryJson(String json,int queryId,String resultMsg){
        DBDao dbDao=DBDao.parseJson(json);
        Gson gson=new Gson();
        QueryDao dao=new QueryDao();
        String[] fileList=new String[0];
        List<Map<String,Object>> dataList=new ArrayList<Map<String, Object>>();
        dao.setContainer("db");
        dao.setSeq(dbDao.getSeq());
        ContainerData containerData=dao.new ContainerData();
        containerData.setCode("selectRes");
        QueryData data=dao.new QueryData();
        data.setResultCode(Integer.toString(queryId));
        data.setResultMsg(resultMsg);
        data.setDataList(dataList);
        containerData.setData(data);
        dao.setContainerData(containerData);

        return gson.toJson(dao);
    }

    public static String getQueryJson(String json,Cursor cursor){
        DBDao dbDao=DBDao.parseJson(json);
        QueryDao dao=new QueryDao();
        dao.setContainer("db");
        dao.setSeq(dbDao.getSeq());
        ContainerData containerData=dao.new ContainerData();
        containerData.setCode("selectRes");
        QueryData data=dao.new QueryData();
        if(cursor!=null){
            data.setResultCode("1");
            data.setResultMsg("success");
            data.setDataList(getDataList(cursor));
        }else if(cursor==null){
            String[] fileList=new String[0];
            List<Map<String,Object>> dataList=new ArrayList<Map<String, Object>>();
            data.setResultCode("0");
            data.setResultMsg("fail");
            data.setDataList(dataList);
        }
        containerData.setData(data);
        dao.setContainerData(containerData);
        //Log.e("out","====QueryJson===="+gson.toJson(dao));
        GsonBuilder builder=new GsonBuilder();
        builder.disableHtmlEscaping();
        return builder.create().toJson(dao);
    }


    //"dataList":[[“1”,”A”,”T”],[“2”,”B”,”T1”]]
    private static List<Map<String,Object>> getDataList(Cursor cursor){
        List<Map<String,Object>> dataList=new ArrayList<Map<String, Object>>();

        String[] name=cursor.getColumnNames();

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            Map<String,Object> map=new HashMap<>();
            for(int i=0;i<name.length;i++){
                try {

                    map.put(name[i], (cursor.getString(cursor.getColumnIndex(name[i]))==null||cursor.getString(cursor.getColumnIndex(name[i])).equals(""))?"":cursor.getString(cursor.getColumnIndex(name[i])));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            dataList.add(map);
        }
        return dataList;
    }
}
