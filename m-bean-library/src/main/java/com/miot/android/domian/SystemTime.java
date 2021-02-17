package com.miot.android.domian;

/**
 * Created by Administrator on 2019/9/27 0027.
 */

public class SystemTime {


    /**
     * body : {"resultMsg":"success","data":{"time":1577176445090},"resultCode":"1"}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * resultMsg : success
         * data : {"time":1577176445090}
         * resultCode : 1
         */

        private String resultMsg;
        private DataBean data;
        private String resultCode;

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public static class DataBean {
            /**
             * time : 1577176445090
             */

            private long time;

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }
        }
    }
}
