package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrivacyPolicyModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<PrivacyModelData> data = null;

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

    public List<PrivacyModelData> getData() {
        return data;
    }

    public void setData(List<PrivacyModelData> data) {
        this.data = data;
    }

    public  class  PrivacyModelData {


        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("terms")
        @Expose
        private String terms;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("modefied_date")
        @Expose
        private String modefiedDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getModefiedDate() {
            return modefiedDate;
        }

        public void setModefiedDate(String modefiedDate) {
            this.modefiedDate = modefiedDate;
        }

    }

}
