package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VenueImagesModel {

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
    private List<VenueImagesData> data = null;

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

    public List<VenueImagesData> getData() {
        return data;
    }

    public void setData(List<VenueImagesData> data) {
        this.data = data;
    }


    public  class  VenueImagesData{

        @SerializedName("gallary_image")
        @Expose
        private String gallaryImage;
        @SerializedName("gallary_image2")
        @Expose
        private String gallaryImage2;
        @SerializedName("gallary_image3")
        @Expose
        private String gallaryImage3;
        @SerializedName("gallary_image4")
        @Expose
        private String gallaryImage4;
        @SerializedName("gallary_image5")
        @Expose
        private String gallaryImage5;
        @SerializedName("gallary_image6")
        @Expose
        private String gallaryImage6;
        @SerializedName("gallary_image7")
        @Expose
        private String gallaryImage7;

        public String getGallaryImage() {
            return gallaryImage;
        }

        public void setGallaryImage(String gallaryImage) {
            this.gallaryImage = gallaryImage;
        }

        public String getGallaryImage2() {
            return gallaryImage2;
        }

        public void setGallaryImage2(String gallaryImage2) {
            this.gallaryImage2 = gallaryImage2;
        }

        public String getGallaryImage3() {
            return gallaryImage3;
        }

        public void setGallaryImage3(String gallaryImage3) {
            this.gallaryImage3 = gallaryImage3;
        }

        public String getGallaryImage4() {
            return gallaryImage4;
        }

        public void setGallaryImage4(String gallaryImage4) {
            this.gallaryImage4 = gallaryImage4;
        }

        public String getGallaryImage5() {
            return gallaryImage5;
        }

        public void setGallaryImage5(String gallaryImage5) {
            this.gallaryImage5 = gallaryImage5;
        }

        public String getGallaryImage6() {
            return gallaryImage6;
        }

        public void setGallaryImage6(String gallaryImage6) {
            this.gallaryImage6 = gallaryImage6;
        }

        public String getGallaryImage7() {
            return gallaryImage7;
        }

        public void setGallaryImage7(String gallaryImage7) {
            this.gallaryImage7 = gallaryImage7;
        }

    }

}
