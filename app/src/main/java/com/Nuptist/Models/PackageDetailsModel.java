package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageDetailsModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PacjkageDetailsData data;

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

    public PacjkageDetailsData getData() {
        return data;
    }

    public void setData(PacjkageDetailsData data) {
        this.data = data;
    }


    public  class  PacjkageDetailsData{


        @SerializedName("packages")
        @Expose
        private Packages packages;
        @SerializedName("offer")
        @Expose
        private List<Offer> offer = null;
        @SerializedName("add_once")
        @Expose
        private List<AddOnce> addOnce = null;
        @SerializedName("venue")
        @Expose
        private List<Venue> venue = null;

        public Packages getPackages() {
            return packages;
        }

        public void setPackages(Packages packages) {
            this.packages = packages;
        }

        public List<Offer> getOffer() {
            return offer;
        }

        public void setOffer(List<Offer> offer) {
            this.offer = offer;
        }

        public List<AddOnce> getAddOnce() {
            return addOnce;
        }

        public void setAddOnce(List<AddOnce> addOnce) {
            this.addOnce = addOnce;
        }

        public List<Venue> getVenue() {
            return venue;
        }

        public void setVenue(List<Venue> venue) {
            this.venue = venue;
        }

        public  class  AddOnce{
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("filepath")
            @Expose
            private String filepath;
            @SerializedName("created_at")
            @Expose
            private String createdAt;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getFilepath() {
                return filepath;
            }

            public void setFilepath(String filepath) {
                this.filepath = filepath;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }



        }

        public  class  Venue {
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
            @SerializedName("parkings_available_2wheeler")
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

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

        }

        public  class  Offer{
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("hindu_wedding_special")
            @Expose
            private String hinduWeddingSpecial;
            @SerializedName("catering")
            @Expose
            private String catering;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("offer_price")
            @Expose
            private String offer_price;

            public String getOffer_price() {
                return offer_price;
            }

            public void setOffer_price(String offer_price) {
                this.offer_price = offer_price;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getHinduWeddingSpecial() {
                return hinduWeddingSpecial;
            }

            public void setHinduWeddingSpecial(String hinduWeddingSpecial) {
                this.hinduWeddingSpecial = hinduWeddingSpecial;
            }

            public String getCatering() {
                return catering;
            }

            public void setCatering(String catering) {
                this.catering = catering;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }


        }

        public class  Packages{

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("package_name")
            @Expose
            private String packageName;
            @SerializedName("start_date")
            @Expose
            private String startDate;
            @SerializedName("offers")
            @Expose
            private String offers;
            @SerializedName("add_once")
            @Expose
            private String addOnce;
            @SerializedName("venue_image_id")
            @Expose
            private String venueImageId;
            @SerializedName("offer_id")
            @Expose
            private String offerId;
            @SerializedName("add_once_id")
            @Expose
            private String addOnceId;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("city")
            @Expose
            private String city;
            @SerializedName("i_liked")
            @Expose
            private int i_liked;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("date")
            @Expose
            private String date;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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

            public int getI_liked() {
                return i_liked;
            }

            public void setI_liked(int i_liked) {
                this.i_liked = i_liked;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getOffers() {
                return offers;
            }

            public void setOffers(String offers) {
                this.offers = offers;
            }

            public String getAddOnce() {
                return addOnce;
            }

            public void setAddOnce(String addOnce) {
                this.addOnce = addOnce;
            }

            public String getVenueImageId() {
                return venueImageId;
            }

            public void setVenueImageId(String venueImageId) {
                this.venueImageId = venueImageId;
            }

            public String getOfferId() {
                return offerId;
            }

            public void setOfferId(String offerId) {
                this.offerId = offerId;
            }

            public String getAddOnceId() {
                return addOnceId;
            }

            public void setAddOnceId(String addOnceId) {
                this.addOnceId = addOnceId;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }


    }

}
