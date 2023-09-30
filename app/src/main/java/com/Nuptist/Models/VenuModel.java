package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VenuModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<VenueData> data = null;
    private final static long serialVersionUID = 1170278485069887260L;

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

    public List<VenueData> getData() {
        return data;
    }

    public void setData(List<VenueData> data) {
        this.data = data;
    }


    public  class  VenueData{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("venue_name")
        @Expose
        private String venueName;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("service_address")
        @Expose
        private String serviceAddress;
        @SerializedName("guest_capycity_min")
        @Expose
        private String guestCapycityMin;
        @SerializedName("guest_capycity_mix")
        @Expose
        private String guestCapycityMix;
        @SerializedName("dining_capycity")
        @Expose
        private String diningCapycity;
        @SerializedName("parking_available_2wheeler")
        @Expose
        private String parkingAvailable2wheeler;
        @SerializedName("parking_available_4wheeler")
        @Expose
        private String parkingAvailable4wheeler;
        @SerializedName("parking_available_3wheeler")
        @Expose
        private String parkingsAvailable2wheeler;
        @SerializedName("venue_feature1")
        @Expose
        private String venueFeature1;
        @SerializedName("venue_feature2")
        @Expose
        private String venueFeature2;
        @SerializedName("venue_feature3")
        @Expose
        private String venueFeature3;
        @SerializedName("venue_feature4")
        @Expose
        private String venueFeature4;
        @SerializedName("venue_feature5")
        @Expose
        private String venueFeature5;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("cover_image")
        @Expose
        private String coverImage;
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
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lang")
        @Expose
        private String lang;
        @SerializedName("venue_packages")
        @Expose
        private String venuePackages;
        @SerializedName("venue_fore_packages")
        @Expose
        private String venueForePackages;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("venue_price")
        @Expose
        private String venue_price;
        @SerializedName("price_per_plate")
        @Expose
        private String price_per_plate;

        public String getPrice_per_plate() {
            return price_per_plate;
        }

        public void setPrice_per_plate(String price_per_plate) {
            this.price_per_plate = price_per_plate;
        }

        public String getVenue_price() {
            return venue_price;
        }

        public void setVenue_price(String venue_price) {
            this.venue_price = venue_price;
        }

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVenueName() {
            return venueName;
        }

        public void setVenueName(String venueName) {
            this.venueName = venueName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getServiceAddress() {
            return serviceAddress;
        }

        public void setServiceAddress(String serviceAddress) {
            this.serviceAddress = serviceAddress;
        }

        public String getGuestCapycityMin() {
            return guestCapycityMin;
        }

        public void setGuestCapycityMin(String guestCapycityMin) {
            this.guestCapycityMin = guestCapycityMin;
        }

        public String getGuestCapycityMix() {
            return guestCapycityMix;
        }

        public void setGuestCapycityMix(String guestCapycityMix) {
            this.guestCapycityMix = guestCapycityMix;
        }

        public String getDiningCapycity() {
            return diningCapycity;
        }

        public void setDiningCapycity(String diningCapycity) {
            this.diningCapycity = diningCapycity;
        }

        public String getParkingAvailable2wheeler() {
            return parkingAvailable2wheeler;
        }

        public void setParkingAvailable2wheeler(String parkingAvailable2wheeler) {
            this.parkingAvailable2wheeler = parkingAvailable2wheeler;
        }

        public String getParkingAvailable4wheeler() {
            return parkingAvailable4wheeler;
        }

        public void setParkingAvailable4wheeler(String parkingAvailable4wheeler) {
            this.parkingAvailable4wheeler = parkingAvailable4wheeler;
        }

        public String getParkingsAvailable2wheeler() {
            return parkingsAvailable2wheeler;
        }

        public void setParkingsAvailable2wheeler(String parkingsAvailable2wheeler) {
            this.parkingsAvailable2wheeler = parkingsAvailable2wheeler;
        }

        public String getVenueFeature1() {
            return venueFeature1;
        }

        public void setVenueFeature1(String venueFeature1) {
            this.venueFeature1 = venueFeature1;
        }

        public String getVenueFeature2() {
            return venueFeature2;
        }

        public void setVenueFeature2(String venueFeature2) {
            this.venueFeature2 = venueFeature2;
        }

        public String getVenueFeature3() {
            return venueFeature3;
        }

        public void setVenueFeature3(String venueFeature3) {
            this.venueFeature3 = venueFeature3;
        }

        public String getVenueFeature4() {
            return venueFeature4;
        }

        public void setVenueFeature4(String venueFeature4) {
            this.venueFeature4 = venueFeature4;
        }

        public String getVenueFeature5() {
            return venueFeature5;
        }

        public void setVenueFeature5(String venueFeature5) {
            this.venueFeature5 = venueFeature5;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getVenuePackages() {
            return venuePackages;
        }

        public void setVenuePackages(String venuePackages) {
            this.venuePackages = venuePackages;
        }

        public String getVenueForePackages() {
            return venueForePackages;
        }

        public void setVenueForePackages(String venueForePackages) {
            this.venueForePackages = venueForePackages;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
