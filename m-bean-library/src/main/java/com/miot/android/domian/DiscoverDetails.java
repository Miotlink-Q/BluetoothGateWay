package com.miot.android.domian;

import java.util.List;

public class DiscoverDetails {


    /**
     * newMessages : 1
     * banners : [{"imgUrl":"http://statics.51miaomiao.com/statics/docs/app/banner/2019/6/8e2e7c1c-562b-4a1a-9cf6-8a7e4ad55424.png","linkType":"2","linkUrl":"http://wap.zhujiash.com:3030/#/menu/index/detail/2","seq":"4","thirdServiceId":"89486b3bacae4cf9a4a6283db54a2d08"},{"imgUrl":"http://statics.51miaomiao.com/statics/docs/app/banner/2019/10/0a4300b8-4a3d-473b-a754-c424c3fbca8f.png","linkType":"2","linkUrl":"http://wap.zhujiash.com:3030/#/menu/index/detail/4","seq":"5","thirdServiceId":"89486b3bacae4cf9a4a6283db54a2d08"}]
     * thirdServices : [{"boutique":[],"id":"21","imgUrl":"http://statics.51miaomiao.com/statics/docs/app/service/2019/4/fee56bd3-ba6e-4e0f-a439-26218f8d6618.png","linkType":"2","more":"更多","moreLinks":"http://wap.zhujiash.com:3030/#/menu/index","name":"到家服务","parentId":0,"seq":"1","serviceOnline":1,"thirdServiceId":"89486b3bacae4cf9a4a6283db54a2d08","thirdServices":[],"unonlineShowName":""},{"boutique":[],"id":"22","imgUrl":"http://statics.51miaomiao.com/statics/docs/app/service/2019/4/6c447ca5-cd91-4344-84ce-0d3e0a6ef7f4.png","linkType":"2","more":"更多","moreLinks":"http://www.0home.cn/weixinshop/weixinContentController.do?CallAppMain&level=1&pageOne=main","name":"妙选商城","parentId":0,"seq":"2","serviceOnline":1,"thirdServiceId":"com.miotlink.app.store","thirdServices":[],"unonlineShowName":""},{"boutique":[],"id":"23","imgUrl":"http://statics.51miaomiao.com/statics/docs/app/service/2019/4/ce7f9def-d133-4b4a-b6cd-e032a7dd1af3.png","linkType":"2","more":"更多","moreLinks":"http://www.baidu.com","name":"第三方服务","parentId":-1,"seq":"3","serviceOnline":0,"thirdServiceId":"com.miotlink.app.store","thirdServices":[],"unonlineShowName":"敬请期待"}]
     */

    private String newMessages;
    private List<BannersBean> banners;
    private List<ThirdServicesBean> thirdServices;

    public String getNewMessages() {
        return newMessages;
    }

    public void setNewMessages(String newMessages) {
        this.newMessages = newMessages;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<ThirdServicesBean> getThirdServices() {
        return thirdServices;
    }

    public void setThirdServices(List<ThirdServicesBean> thirdServices) {
        this.thirdServices = thirdServices;
    }

    public static class BannersBean {
        /**
         * imgUrl : http://statics.51miaomiao.com/statics/docs/app/banner/2019/6/8e2e7c1c-562b-4a1a-9cf6-8a7e4ad55424.png
         * linkType : 2
         * linkUrl : http://wap.zhujiash.com:3030/#/menu/index/detail/2
         * seq : 4
         * thirdServiceId : 89486b3bacae4cf9a4a6283db54a2d08
         */

        private String imgUrl;
        private String linkType;
        private String linkUrl;
        private String seq;
        private String thirdServiceId;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLinkType() {
            return linkType;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public String getThirdServiceId() {
            return thirdServiceId;
        }

        public void setThirdServiceId(String thirdServiceId) {
            this.thirdServiceId = thirdServiceId;
        }
    }

    public static class ThirdServicesBean {
        /**
         * boutique : []
         * id : 21
         * imgUrl : http://statics.51miaomiao.com/statics/docs/app/service/2019/4/fee56bd3-ba6e-4e0f-a439-26218f8d6618.png
         * linkType : 2
         * more : 更多
         * moreLinks : http://wap.zhujiash.com:3030/#/menu/index
         * name : 到家服务
         * parentId : 0
         * seq : 1
         * serviceOnline : 1
         * thirdServiceId : 89486b3bacae4cf9a4a6283db54a2d08
         * thirdServices : []
         * unonlineShowName :
         */

        private String id;
        private String imgUrl;
        private String linkType;
        private String more;
        private String moreLinks;
        private String name;
        private int parentId;
        private String seq;
        private int serviceOnline;
        private String thirdServiceId;
        private String unonlineShowName;
        private List<?> boutique;
        private List<?> thirdServices;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLinkType() {
            return linkType;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public String getMoreLinks() {
            return moreLinks;
        }

        public void setMoreLinks(String moreLinks) {
            this.moreLinks = moreLinks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public int getServiceOnline() {
            return serviceOnline;
        }

        public void setServiceOnline(int serviceOnline) {
            this.serviceOnline = serviceOnline;
        }

        public String getThirdServiceId() {
            return thirdServiceId;
        }

        public void setThirdServiceId(String thirdServiceId) {
            this.thirdServiceId = thirdServiceId;
        }

        public String getUnonlineShowName() {
            return unonlineShowName;
        }

        public void setUnonlineShowName(String unonlineShowName) {
            this.unonlineShowName = unonlineShowName;
        }

        public List<?> getBoutique() {
            return boutique;
        }

        public void setBoutique(List<?> boutique) {
            this.boutique = boutique;
        }

        public List<?> getThirdServices() {
            return thirdServices;
        }

        public void setThirdServices(List<?> thirdServices) {
            this.thirdServices = thirdServices;
        }
    }
}
