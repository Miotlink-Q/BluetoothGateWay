package com.miot.android.domian;

import android.text.TextUtils;


import com.miot.android.utils.FileManager;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class PictureBean {

    /**
     * loading : {"startTime":"","id":"0","cancelTime":"0","img":"","endTime":"","type":"img","url":"","fullTime":"0"}
     * scene : {"id":"47","startTime":"2017-05-11 13:27:07.0","endTime":"2018-05-12 13:26:50.0","imgArray":[{"index":"1","img":"http://60.191.23.28:88/statics/docs/app/img/loding_bg.png","url":"https://www.taobao.com/"},{"index":"2","img":"http://60.191.23.28:88/statics/docs/app/img/logo_bx.png","url":"https://www.baidu.com/"},{"index":"3","img":"http://60.191.23.28:88/statics/docs/app/img/mj/bg.png","url":"http://www.taokaisen.com/"}]}
     * login : {"startTime":"2017-05-11 13:22:12.0","id":"48","img":"http://60.191.23.28:88/statics/docs/app/img/logo_bx.png","endTime":"2018-05-12 13:22:14.0","type":"img","url":null}
     */

    private LoadingBean loading;
    private SceneBean scene;
    private LoginBean login;

    public LoadingBean getLoading() {
        return loading;
    }

    public void setLoading(LoadingBean loading) {
        this.loading = loading;
    }

    public SceneBean getScene() {
        return scene;
    }

    public void setScene(SceneBean scene) {
        this.scene = scene;
    }

    public LoginBean getLogin() {
        return login;
    }

    public void setLogin(LoginBean login) {
        this.login = login;
    }

    public static class LoadingBean {
        /**
         * startTime :
         * id : 0
         * cancelTime : 0
         * img :
         * endTime :
         * type : img
         * url :
         * fullTime : 0
         */

        private String startTime;
        private String id;
        private String cancelTime;
        private String img;
        private String endTime;
        private String opType;
        private String url;
        private String fullTime;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getImg() {
            if (!TextUtils.isEmpty(img) && img.startsWith("http")) {
                File file = new File(FileManager.PATH_IMAGE_SCREEN + "/" + FileManager.splitUrl(img, new String[]{"jpg", "png"}));
                if (file != null && file.isFile()) {
                    img = file.getAbsolutePath();
                }
            }
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getType() {
            return opType;
        }

        public void setType(String type) {
            this.opType = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFullTime() {
            return fullTime;
        }

        public void setFullTime(String fullTime) {
            this.fullTime = fullTime;
        }
    }

    public static class SceneBean {
        /**
         * id : 47
         * startTime : 2017-05-11 13:27:07.0
         * endTime : 2018-05-12 13:26:50.0
         * imgArray : [{"index":"1","img":"http://60.191.23.28:88/statics/docs/app/img/loding_bg.png","url":"https://www.taobao.com/"},{"index":"2","img":"http://60.191.23.28:88/statics/docs/app/img/logo_bx.png","url":"https://www.baidu.com/"},{"index":"3","img":"http://60.191.23.28:88/statics/docs/app/img/mj/bg.png","url":"http://www.taokaisen.com/"}]
         */

        private String id;
        private String startTime;
        private String endTime;
        private List<ImgArrayBean> imgArray;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public List<ImgArrayBean> getImgArray() {
            return imgArray;
        }

        public void setImgArray(List<ImgArrayBean> imgArray) {
            this.imgArray = imgArray;
        }

        public static class ImgArrayBean {
            /**
             * index : 1
             * img : http://60.191.23.28:88/statics/docs/app/img/loding_bg.png
             * url : https://www.taobao.com/
             */

            private String index;
            private String img;
            private String url;

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class LoginBean {
        /**
         * startTime : 2017-05-11 13:22:12.0
         * id : 48
         * img : http://60.191.23.28:88/statics/docs/app/img/logo_bx.png
         * endTime : 2018-05-12 13:22:14.0
         * type : img
         * url : null
         */

        private String startTime;
        private String id;
        private String img;
        private String endTime;
        private String type;
        private Object url;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            if (!TextUtils.isEmpty(img) && img.startsWith("http")) {
                File file = new File(FileManager.PATH_IMAGE_SCREEN + "/" + FileManager.splitUrl(img, new String[]{"jpg", "png"}));
                if (file != null && file.isFile()) {
                    img = file.getAbsolutePath();
                }
            }
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }
}
