package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchPackageModel implements Serializable {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<SearchData> data = null;

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

    public List<SearchData> getData() {
        return data;
    }

    public void setData(List<SearchData> data) {
        this.data = data;
    }


    public  class  SearchData  implements Serializable{

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
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("users_status")
        @Expose
        private String users_status;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        @Override
        public String toString() {
            return "SearchData{" +
                    "id='" + id + '\'' +
                    ", guestCapycityMix='" + guestCapycityMix + '\'' +
                    ", packageId='" + packageId + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", price='" + price + '\'' +
                    ", date='" + date + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
