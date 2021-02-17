package com.miot.android.data.d.db.DBUtils.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xdf on 2016/6/30.
 */
public class CreateTableOrDeleteOrUpdateDao {
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

    public class ContainerData {
        private String code;
        private DeleteOrCreateOrUpdateData data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DeleteOrCreateOrUpdateData getData() {
            return data;
        }

        public void setData(DeleteOrCreateOrUpdateData data) {
            this.data = data;
        }
    }

    public class DeleteOrCreateOrUpdateData {
        private String resultCode;
        private String resultMsg;
        private String effectLines;

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

        public String getEffectLines() {
            return effectLines;
        }

        public void setEffectLines(String effectLines) {
            this.effectLines = effectLines;
        }
    }

    public static String getCreateTableOrDeleteJson(String json, String resultCode, String resultMsg, String effectLines) {
        DBDao dbDao = DBDao.parseJson(json);
        Gson gson = new Gson();
        CreateTableOrDeleteOrUpdateDao dao = new CreateTableOrDeleteOrUpdateDao();
        dao.setContainer("db");
        dao.setSeq(dbDao.getSeq());
        ContainerData containerData = dao.new ContainerData();
        if (dbDao.getContainerData().getCode().equals("execute")) {
            containerData.setCode("executeRes");
        } else if (dbDao.getContainerData().getCode().equals("delete")) {
            containerData.setCode("deleteRes");
        } else if (dbDao.getContainerData().getCode().equals("update")) {
            containerData.setCode("updateRes");
        }
        DeleteOrCreateOrUpdateData data = dao.new DeleteOrCreateOrUpdateData();
        data.setResultCode(resultCode);
        data.setResultMsg(resultMsg);
        data.setEffectLines(effectLines);
        containerData.setData(data);
        dao.setContainerData(containerData);

        return gson.toJson(dao);
    }

    public static String getReplacedJson(String json, String resultCode, String resultMsg, String effectLines) {

        CreateTableOrDeleteOrUpdateDao dao = new CreateTableOrDeleteOrUpdateDao();
        try {
            JSONObject obj = new JSONObject(json);
            String seq = (String) obj.get("seq");
            dao.setContainer("db");
            dao.setSeq(seq);
            ContainerData containerData = dao.new ContainerData();
            containerData.setCode("replaceRes");
            DeleteOrCreateOrUpdateData data = dao.new DeleteOrCreateOrUpdateData();
            data.setResultCode(resultCode);
            data.setResultMsg(resultMsg);
            data.setEffectLines(effectLines);
            containerData.setData(data);
            dao.setContainerData(containerData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(dao);
    }
}
