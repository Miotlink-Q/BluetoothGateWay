package com.miot.android.data.d.db.DBUtils.entity;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by xdf on 2016/6/29.
 */
public class DBDao {


    public static String DB_CRERATE_TABLE = "execute";
    public static String DB_INSERT = "insert";
    public static String DB_DELETE = "delete";
    public static String DB_UPDATE = "update";
    public static String DB_QUERY = "select";
    public static String DB_REPLACE = "replace";
    public static String DB_BATCH_INSERT = "batchInsert";
    public static String DB_BATCH_DELETE = "batchDelete";
    public static String DB_BATCH_UPDATE = "batchUpdate";
    public static String DB_BATCH_QUERY = "batchSelect";
    public static String DB_BATCH_REPLACE = "batchReplace";


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

    public class Data {
        private String sql;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }
    }

    public static DBDao parseJson(String json) {
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, DBDao.class);
        }
        return null;
    }

    public static int DBDaoParseCode(String json) {
        CommonBean commonBean = new Gson().fromJson(json, CommonBean.class);
        if (commonBean == null) {
            return -1;
        }
        String code=commonBean.getContainerData().getCode();
        if (!TextUtils.isEmpty(code)) {
            int m = -1;
            if (DB_CRERATE_TABLE.equals(code)) {
                m = 0;
            } else if (DB_INSERT.equals(code)) {
                m = 1;
            } else if (DB_DELETE.equals(code)) {
                m = 2;
            } else if (DB_UPDATE.equals(code)) {
                m = 3;
            } else if (DB_QUERY.equals(code)) {
                m = 4;
            } else if (DB_REPLACE.equals(code)) {
                m = 5;
            } else if (DB_BATCH_INSERT.equals(code)) {
                m = 6;
            } else if (DB_BATCH_DELETE.equals(code)) {
                m = 7;
            } else if (DB_BATCH_UPDATE.equals(code)) {
                m = 8;
            } else if (DB_BATCH_QUERY.equals(code)) {
                m = 9;
            } else if (DB_BATCH_REPLACE.equals(code)) {
                m = 10;
            }
            return m;
        }
        return -1;
    }
}
