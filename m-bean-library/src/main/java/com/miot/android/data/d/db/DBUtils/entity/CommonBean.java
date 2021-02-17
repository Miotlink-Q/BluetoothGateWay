package com.miot.android.data.d.db.DBUtils.entity;

/**
 * Created by Miotlink on 2016/6/28.
 */
public class CommonBean {
    /**
     * container : webservice
     * containerData : {"code":"send","data":{}}
     */

    private String container="";

    private String seq="";

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    /**
     * code : send
     * data : {}
     */

    private ContainerDataBean containerData=null;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public ContainerDataBean getContainerData() {
        return containerData;
    }

    public void setContainerData(ContainerDataBean containerData) {
        this.containerData = containerData;
    }

    public static class ContainerDataBean {
        private String code="";

        public String getCode() {

            return code;
        }

        public void setCode(String code) {

            this.code = code;
        }
    }
}
