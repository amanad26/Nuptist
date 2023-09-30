package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyBookingModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<MyBookingData> data = null;

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

    public List<MyBookingData> getData() {
        return data;
    }

    public void setData(List<MyBookingData> data) {
        this.data = data;
    }

    public  class  MyBookingData{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("addonce_id")
        @Expose
        private String addonceId;
        @SerializedName("total_package")
        @Expose
        private String totalPackage;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("booking_status")
        @Expose
        private String bookingStatus;
        @SerializedName("payment_method")
        @Expose
        private String paymentMethod;
        @SerializedName("booking_date")
        @Expose
        private String bookingDate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("transaction_id")
        @Expose
        private String transactionId;
        @SerializedName("status_payment")
        @Expose
        private String statusPayment;
        @SerializedName("package_status")
        @Expose
        private String packageStatus;
        @SerializedName("package_name")
        @Expose
        private String package_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
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

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getAddonceId() {
            return addonceId;
        }

        public void setAddonceId(String addonceId) {
            this.addonceId = addonceId;
        }

        public String getTotalPackage() {
            return totalPackage;
        }

        public void setTotalPackage(String totalPackage) {
            this.totalPackage = totalPackage;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getBookingDate() {
            return bookingDate;
        }

        public void setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getStatusPayment() {
            return statusPayment;
        }

        public void setStatusPayment(String statusPayment) {
            this.statusPayment = statusPayment;
        }

        public String getPackageStatus() {
            return packageStatus;
        }

        public void setPackageStatus(String packageStatus) {
            this.packageStatus = packageStatus;
        }
    }
}
