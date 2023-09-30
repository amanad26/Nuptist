package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyFavoritePackageModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("data")
    @Expose
    private List<FavoritePackageData> data = null;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FavoritePackageData> getData() {
        return data;
    }

    public void setData(List<FavoritePackageData> data) {
        this.data = data;
    }

    public  class  FavoritePackageData{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("fav_package_name")
        @Expose
        private String favPackageName;
        @SerializedName("fav_package_image")
        @Expose
        private String favPackageImage;
        @SerializedName("fav_package_address")
        @Expose
        private String favPackageAddress;
        @SerializedName("fav_package_start_date")
        @Expose
        private String favPackageStartDate;
        @SerializedName("fav_venue_capacity")
        @Expose
        private String favVenueCapacity;
        @SerializedName("total_user_count")
        @Expose
        private String totalUserCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getFavPackageName() {
            return favPackageName;
        }

        public void setFavPackageName(String favPackageName) {
            this.favPackageName = favPackageName;
        }

        public String getFavPackageImage() {
            return favPackageImage;
        }

        public void setFavPackageImage(String favPackageImage) {
            this.favPackageImage = favPackageImage;
        }

        public String getFavPackageAddress() {
            return favPackageAddress;
        }

        public void setFavPackageAddress(String favPackageAddress) {
            this.favPackageAddress = favPackageAddress;
        }

        public String getFavPackageStartDate() {
            return favPackageStartDate;
        }

        public void setFavPackageStartDate(String favPackageStartDate) {
            this.favPackageStartDate = favPackageStartDate;
        }

        public String getFavVenueCapacity() {
            return favVenueCapacity;
        }

        public void setFavVenueCapacity(String favVenueCapacity) {
            this.favVenueCapacity = favVenueCapacity;
        }

        public String getTotalUserCount() {
            return totalUserCount;
        }

        public void setTotalUserCount(String totalUserCount) {
            this.totalUserCount = totalUserCount;
        }
    }
}
