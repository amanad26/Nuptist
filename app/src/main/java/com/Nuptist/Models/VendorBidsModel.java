package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorBidsModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<VendorBidData> data = null;

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

    public List<VendorBidData> getData() {
        return data;
    }

    public void setData(List<VendorBidData> data) {
        this.data = data;
    }


    public  class  VendorBidData{
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("cover_image")
        @Expose
        private String coverImage;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("bids_id")
        @Expose
        private String bidsId;
        @SerializedName("bids_price")
        @Expose
        private String bidsPrice;
        @SerializedName("vender_id")
        @Expose
        private String vender_id;

        public String getVender_id() {
            return vender_id;
        }

        public void setVender_id(String vender_id) {
            this.vender_id = vender_id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getBidsId() {
            return bidsId;
        }

        public void setBidsId(String bidsId) {
            this.bidsId = bidsId;
        }

        public String getBidsPrice() {
            return bidsPrice;
        }

        public void setBidsPrice(String bidsPrice) {
            this.bidsPrice = bidsPrice;
        }


    }

}
