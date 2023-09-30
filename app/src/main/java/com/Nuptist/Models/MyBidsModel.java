package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyBidsModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<BidData> data = null;

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

    public List<BidData> getData() {
        return data;
    }

    public void setData(List<BidData> data) {
        this.data = data;
    }

    public  class BidData{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("vender_id")
        @Expose
        private String venderId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("package_name")
        @Expose
        private Object packageName;
        @SerializedName("start_date")
        @Expose
        private Object startDate;
        @SerializedName("offers")
        @Expose
        private String offers;
        @SerializedName("add_once")
        @Expose
        private String addOnce;
        @SerializedName("venue_image_id")
        @Expose
        private Object venueImageId;
        @SerializedName("venuname_id")
        @Expose
        private Object venunameId;
        @SerializedName("venueprice_id")
        @Expose
        private Object venuepriceId;
        @SerializedName("address")
        @Expose
        private Object address;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("users_status")
        @Expose
        private String usersStatus;
        @SerializedName("minimum_price")
        @Expose
        private String minimumPrice;
        @SerializedName("maximum_price")
        @Expose
        private String maximumPrice;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getVenderId() {
            return venderId;
        }

        public void setVenderId(String venderId) {
            this.venderId = venderId;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getPackageName() {
            return packageName;
        }

        public void setPackageName(Object packageName) {
            this.packageName = packageName;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public String getOffers() {
            return offers;
        }

        public void setOffers(String offers) {
            this.offers = offers;
        }

        public String getAddOnce() {
            return addOnce;
        }

        public void setAddOnce(String addOnce) {
            this.addOnce = addOnce;
        }

        public Object getVenueImageId() {
            return venueImageId;
        }

        public void setVenueImageId(Object venueImageId) {
            this.venueImageId = venueImageId;
        }

        public Object getVenunameId() {
            return venunameId;
        }

        public void setVenunameId(Object venunameId) {
            this.venunameId = venunameId;
        }

        public Object getVenuepriceId() {
            return venuepriceId;
        }

        public void setVenuepriceId(Object venuepriceId) {
            this.venuepriceId = venuepriceId;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUsersStatus() {
            return usersStatus;
        }

        public void setUsersStatus(String usersStatus) {
            this.usersStatus = usersStatus;
        }

        public String getMinimumPrice() {
            return minimumPrice;
        }

        public void setMinimumPrice(String minimumPrice) {
            this.minimumPrice = minimumPrice;
        }

        public String getMaximumPrice() {
            return maximumPrice;
        }

        public void setMaximumPrice(String maximumPrice) {
            this.maximumPrice = maximumPrice;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
