package com.miot.android.data.d.db.DBUtils.entity;



import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdf on 2016/9/14.
 */
public class BatchInsertDao {

    private String container;
    private String seq;
    private ContainerData containerData;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public ContainerData getContainerData() {
        return containerData;
    }

    public void setContainerData(ContainerData containerData) {
        this.containerData = containerData;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
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
        private List<String> sqlList;

        public List<String> getSqlList() {
            return sqlList;
        }

        public void setSqlList(List<String> sqlList) {
            this.sqlList = sqlList;
        }
    }

    public static List<String> obtainBatchSqlData(String json) {
        //int code=DBDao.DBDaoParseCode(json);
        Gson gson = new Gson();
        List<String> batchList = new ArrayList<>();
        BatchInsertDao batchInsertDao = gson.fromJson(json, BatchInsertDao.class);
        List<String> sqlList = batchInsertDao.getContainerData().getData().getSqlList();
        batchList.clear();
        for (String sql : sqlList) {
            batchList.add(sql);
        }
        return batchList;
//        switch (code){
//            case 6:
//                for (String  sql : sqlList) {
//                    batchList.add(sql);
//                }
//                break;
//            case 7:
//                for (String sql : sqlList) {
//                    batchList.add(sql);
//                }
//                break;
//            case 8:
//                for (String sql : sqlList) {
//                    batchList.add(sql);
//                }
//                break;
//            case 9:
//                for (String sql : sqlList) {
//                    batchList.add(sql);
//                }
//                break;
//        }
//        return batchList;
    }
}
