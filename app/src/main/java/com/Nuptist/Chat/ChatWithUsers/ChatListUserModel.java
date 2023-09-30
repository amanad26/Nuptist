package com.Nuptist.Chat.ChatWithUsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatListUserModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ChatListUserData> data = null;

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

    public List<ChatListUserData> getData() {
        return data;
    }

    public void setData(List<ChatListUserData> data) {
        this.data = data;
    }


    public  class  ChatListUserData{



        @SerializedName("vender_name")
        @Expose
        private String venderName;
        @SerializedName("vender_image")
        @Expose
        private String venderImage;



        public String getVenderName() {
            return venderName;
        }

        public void setVenderName(String venderName) {
            this.venderName = venderName;
        }

        public String getVenderImage() {
            return venderImage;
        }

        public void setVenderImage(String venderImage) {
            this.venderImage = venderImage;
        }



        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("vender_id")
        @Expose
        private String venderId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("fcm_id")
        @Expose
        private String fcmId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVenderId() {
            return venderId;
        }

        public void setVenderId(String venderId) {
            this.venderId = venderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
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

        public String getFcmId() {
            return fcmId;
        }

        public void setFcmId(String fcmId) {
            this.fcmId = fcmId;
        }
    }

}
