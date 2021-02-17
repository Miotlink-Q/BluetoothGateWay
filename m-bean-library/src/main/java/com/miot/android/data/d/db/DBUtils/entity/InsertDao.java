package com.miot.android.data.d.db.DBUtils.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xdf on 2016/6/30.
 */
public class InsertDao {
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
        private InsertData data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public InsertData getData() {
            return data;
        }

        public void setData(InsertData data) {
            this.data = data;
        }
    }

    public class InsertData{
        private String resultCode;
        private String resultMsg;
        private String primaryKey;

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

        public String getPrimarykey() {
            return primaryKey;
        }

        public void setPrimarykey(String primaryKey) {
            this.primaryKey = primaryKey;
        }
    }

    public static String getInsertJson(String json,String resultCode,String resultMsg,String primaryKey){
        DBDao dbDao=DBDao.parseJson(json);
        Gson gson=new Gson();
        InsertDao dao=new InsertDao();
        dao.setContainer("db");
        dao.setSeq(dbDao.getSeq());
        ContainerData containerData=dao.new ContainerData();
        containerData.setCode("batchInsertRes");
        dao.setContainerData(containerData);
        InsertData data=dao.new InsertData();
        data.setResultCode(resultCode);
        data.setResultMsg(resultMsg);
        data.setPrimarykey(primaryKey);
        containerData.setData(data);

        return gson.toJson(dao);
    }


    public static String getReplacedJson(String json,String resultCode,String resultMsg,String primaryKey){

        InsertDao dao=new InsertDao();
        try {
            JSONObject obj=new JSONObject(json);
            String seq= (String) obj.get("seq");
            dao.setContainer("db");
            dao.setSeq(seq);
            ContainerData containerData=dao.new ContainerData();
            containerData.setCode("replaceRes");
            InsertData data=dao.new InsertData();
            data.setResultCode(resultCode);
            data.setResultMsg(resultMsg);
            data.setPrimarykey(primaryKey);
            containerData.setData(data);
            dao.setContainerData(containerData);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Gson().toJson(dao);
    }

}
