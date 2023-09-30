package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageByRatingModel {


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
    private List<PackageRatingData> data = null;

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

    public List<PackageRatingData> getData() {
        return data;
    }

    public void setData(List<PackageRatingData> data) {
        this.data = data;
    }


    public  class  PackageRatingData{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("guest_capycity_mix")
        @Expose
        private String guestCapycityMix;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("package_name")
        @Expose
        private String package_name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("rating")
        @Expose
        private String rating;

        @SerializedName("users_status")
        @Expose
        private String users_status;

        @SerializedName("average_rating")
        @Expose
        private String average_rating;


        public String getAverage_rating() {
            return average_rating;
        }

        public void setAverage_rating(String average_rating) {
            this.average_rating = average_rating;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getUsers_status() {
            return users_status;
        }

        public void setUsers_status(String users_status) {
            this.users_status = users_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGuestCapycityMix() {
            return guestCapycityMix;
        }

        public void setGuestCapycityMix(String guestCapycityMix) {
            this.guestCapycityMix = guestCapycityMix;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }


    }

}
