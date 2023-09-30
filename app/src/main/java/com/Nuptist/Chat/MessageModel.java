package com.Nuptist.Chat;

public class MessageModel {

    String sender , reciver , message , time , date , id ;

    public MessageModel(String sender, String reciver, String message, String time, String date, String id) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
        this.time = time;
        this.date = date;
        this.id = id;
    }

    public MessageModel() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
