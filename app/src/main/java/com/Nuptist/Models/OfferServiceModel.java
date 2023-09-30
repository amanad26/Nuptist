package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferServiceModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private OfferServiceData OfferServiceData = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OfferServiceModel.OfferServiceData getOfferServiceData() {
        return OfferServiceData;
    }

    public void setOfferServiceData(OfferServiceModel.OfferServiceData offerServiceData) {
        OfferServiceData = offerServiceData;
    }

    public  class  OfferServiceData{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("offer_details_id")
        @Expose
        private String offerDetailsId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("recommended_price")
        @Expose
        private String recommendedPrice;
        @SerializedName("main_service1")
        @Expose
        private String mainService1;
        @SerializedName("main_service2")
        @Expose
        private String mainService2;
        @SerializedName("main_service3")
        @Expose
        private String mainService3;
        @SerializedName("no_of_workers")
        @Expose
        private String noOfWorkers;
        @SerializedName("task_duration")
        @Expose
        private String taskDuration;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("create_at")
        @Expose
        private String createAt;
        @SerializedName("vender_status")
        @Expose
        private String venderStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOfferDetailsId() {
            return offerDetailsId;
        }

        public void setOfferDetailsId(String offerDetailsId) {
            this.offerDetailsId = offerDetailsId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRecommendedPrice() {
            return recommendedPrice;
        }

        public void setRecommendedPrice(String recommendedPrice) {
            this.recommendedPrice = recommendedPrice;
        }

        public String getMainService1() {
            return mainService1;
        }

        public void setMainService1(String mainService1) {
            this.mainService1 = mainService1;
        }

        public String getMainService2() {
            return mainService2;
        }

        public void setMainService2(String mainService2) {
            this.mainService2 = mainService2;
        }

        public String getMainService3() {
            return mainService3;
        }

        public void setMainService3(String mainService3) {
            this.mainService3 = mainService3;
        }

        public String getNoOfWorkers() {
            return noOfWorkers;
        }

        public void setNoOfWorkers(String noOfWorkers) {
            this.noOfWorkers = noOfWorkers;
        }

        public String getTaskDuration() {
            return taskDuration;
        }

        public void setTaskDuration(String taskDuration) {
            this.taskDuration = taskDuration;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getVenderStatus() {
            return venderStatus;
        }

        public void setVenderStatus(String venderStatus) {
            this.venderStatus = venderStatus;
        }


    }

}
