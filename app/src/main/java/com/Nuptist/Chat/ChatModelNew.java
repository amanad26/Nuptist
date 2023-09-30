package com.Nuptist.Chat;

public class ChatModelNew {


    String command_id ,  image , message ,plan_order_id ,receiveerID ,recording ,senderID
                ,status,time ,username ,video ;


    public ChatModelNew(String command_id, String image, String message, String plan_order_id, String receiveerID, String recording, String senderID, String status, String time, String username, String video) {
        this.command_id = command_id;
        this.image = image;
        this.message = message;
        this.plan_order_id = plan_order_id;
        this.receiveerID = receiveerID;
        this.recording = recording;
        this.senderID = senderID;
        this.status = status;
        this.time = time;
        this.username = username;
        this.video = video;
    }

    public  ChatModelNew(){

    }

    public String getCommand_id() {
        return command_id;
    }

    public void setCommand_id(String command_id) {
        this.command_id = command_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlan_order_id() {
        return plan_order_id;
    }

    public void setPlan_order_id(String plan_order_id) {
        this.plan_order_id = plan_order_id;
    }

    public String getReceiveerID() {
        return receiveerID;
    }

    public void setReceiveerID(String receiveerID) {
        this.receiveerID = receiveerID;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
