package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentPackagesModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<RecentPackageData> data = null;

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

    public List<RecentPackageData> getData() {
        return data;
    }

    public void setData(List<RecentPackageData> data) {
        this.data = data;
    }
    
    public class  RecentPackageData{

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("package_name")
        @Expose
        private String packageName;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("users_status")
        @Expose
        private String usersStatus  = null;
        @SerializedName("date")
        @Expose
        private String date;

        @Override
        public String toString() {
            return "RecentPackageData{" +
                    "userId='" + userId + '\'' +
                    ", packageId='" + packageId + '\'' +
                    ", image='" + image + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", status='" + status + '\'' +
                    ", usersStatus='" + usersStatus + '\'' +
                    ", date='" + date + '\'' +
                    '}';
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
