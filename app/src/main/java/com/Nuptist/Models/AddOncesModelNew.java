package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddOncesModelNew {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")

    @Expose
    private AddOnceData data;

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

    public AddOnceData getData() {
        return data;
    }

    public void setData(AddOnceData data) {
        this.data = data;
    }


    public  class  AddOnceData{

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
        private List<AllAddOnces> addOnce = null;
        @SerializedName("venue_image_id")
        @Expose
        private String venueImageId;
        @SerializedName("venuname_id")
        @Expose
        private String venunameId;
        @SerializedName("venueprice_id")
        @Expose
        private String venuepriceId;
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
        private String usersStatus;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("minimum_price")
        @Expose
        private Object minimumPrice;
        @SerializedName("maximum_price")
        @Expose
        private Object maximumPrice;
        @SerializedName("date")
        @Expose
        private Object date;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("venue_price")
        @Expose
        private String venuePrice;

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

        public List<AllAddOnces> getAddOnce() {
            return addOnce;
        }

        public void setAddOnce(List<AllAddOnces> addOnce) {
            this.addOnce = addOnce;
        }

        public String getVenueImageId() {
            return venueImageId;
        }

        public void setVenueImageId(String venueImageId) {
            this.venueImageId = venueImageId;
        }

        public String getVenunameId() {
            return venunameId;
        }

        public void setVenunameId(String venunameId) {
            this.venunameId = venunameId;
        }

        public String getVenuepriceId() {
            return venuepriceId;
        }

        public void setVenuepriceId(String venuepriceId) {
            this.venuepriceId = venuepriceId;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getMinimumPrice() {
            return minimumPrice;
        }

        public void setMinimumPrice(Object minimumPrice) {
            this.minimumPrice = minimumPrice;
        }

        public Object getMaximumPrice() {
            return maximumPrice;
        }

        public void setMaximumPrice(Object maximumPrice) {
            this.maximumPrice = maximumPrice;
        }

        public Object getDate() {
            return date;
        }

        public void setDate(Object date) {
            this.date = date;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getVenuePrice() {
            return venuePrice;
        }

        public void setVenuePrice(String venuePrice) {
            this.venuePrice = venuePrice;
        }


        public  class  AllAddOnces{

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("data")
            @Expose
            private PackageDataInAddonce data;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public PackageDataInAddonce getData() {
                return data;
            }

            public void setData(PackageDataInAddonce data) {
                this.data = data;
            }


            public  class  PackageDataInAddonce{

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("addonce_id")
                @Expose
                private String addonceId;
                @SerializedName("title")
                @Expose
                private String title;
                @SerializedName("image")
                @Expose
                private String image;
                @SerializedName("mini_price")
                @Expose
                private String miniPrice;
                @SerializedName("maxi_price")
                @Expose
                private String maxiPrice;
                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("created_at")
                @Expose
                private String createdAt;
                @SerializedName("count")
                @Expose
                private String count;
                @SerializedName("bid_max")
                @Expose
                private String bid_max;
                @SerializedName("bid_min")
                @Expose
                private String bid_min;
                @SerializedName("user_price")
                @Expose
                private String user_price;

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getBid_max() {
                    return bid_max;
                }

                public void setBid_max(String bid_max) {
                    this.bid_max = bid_max;
                }

                public String getBid_min() {
                    return bid_min;
                }

                public void setBid_min(String bid_min) {
                    this.bid_min = bid_min;
                }

                public String getUser_price() {
                    return user_price;
                }

                public void setUser_price(String user_price) {
                    this.user_price = user_price;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAddonceId() {
                    return addonceId;
                }

                public void setAddonceId(String addonceId) {
                    this.addonceId = addonceId;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getMiniPrice() {
                    return miniPrice;
                }

                public void setMiniPrice(String miniPrice) {
                    this.miniPrice = miniPrice;
                }

                public String getMaxiPrice() {
                    return maxiPrice;
                }

                public void setMaxiPrice(String maxiPrice) {
                    this.maxiPrice = maxiPrice;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }
            }

        }

    }

}

