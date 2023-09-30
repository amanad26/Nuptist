package com.Nuptist.calender.AddOnceNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FinalAddonsNewModel implements Serializable {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private FinalAddOnsData data;

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

    public FinalAddOnsData getData() {
        return data;
    }

    public void setData(FinalAddOnsData data) {
        this.data = data;
    }

    public  class  FinalAddOnsData   implements Serializable{

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
        @SerializedName("service_id")
        @Expose
        private List<ServiceId> serviceId = null;
        @SerializedName("product_id")
        @Expose
        private List<ProductId> productId = null;
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
        private Object address;
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

        @SerializedName("service_offer_details_id")
        @Expose
        private List<ServiceOfferDetailsId> serviceOfferDetailsId = null;
        @SerializedName("product_offer_details_id")
        @Expose
        private List<ProductOfferDetailsId> productOfferDetailsId = null;
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

        public List<ServiceId> getServiceId() {
            return serviceId;
        }

        public void setServiceId(List<ServiceId> serviceId) {
            this.serviceId = serviceId;
        }

        public List<ProductId> getProductId() {
            return productId;
        }

        public void setProductId(List<ProductId> productId) {
            this.productId = productId;
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

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
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

        public class ProductId implements Serializable{

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("add_once_details_id")
            @Expose
            private String addOnceDetailsId;
            @SerializedName("data")
            @Expose
            private Data__2 data;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddOnceDetailsId() {
                return addOnceDetailsId;
            }

            public void setAddOnceDetailsId(String addOnceDetailsId) {
                this.addOnceDetailsId = addOnceDetailsId;
            }

            public Data__2 getData() {
                return data;
            }

            public void setData(Data__2 data) {
                this.data = data;
            }

            public class Data__2 implements Serializable{

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("add_once_details_id")
                @Expose
                private String addOnceDetailsId;
                @SerializedName("product_name")
                @Expose
                private String productName;
                @SerializedName("image")
                @Expose
                private String image;
                @SerializedName("recommended_price")
                @Expose
                private String recommendedPrice;
                @SerializedName("role_product")
                @Expose
                private String roleProduct;
                @SerializedName("main_product1")
                @Expose
                private String mainProduct1;
                @SerializedName("main_product2")
                @Expose
                private String mainProduct2;
                @SerializedName("main_product3")
                @Expose
                private String mainProduct3;
                @SerializedName("material_qty")
                @Expose
                private String materialQty;
                @SerializedName("unit")
                @Expose
                private String unit;
                @SerializedName("description")
                @Expose
                private String description;
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
                @SerializedName("vender_status")
                @Expose
                private String vender_status;

                boolean isselected = false ;

                public boolean isIsselected() {
                    return isselected;
                }

                public void setIsselected(boolean isselected) {
                    this.isselected = isselected;
                }


                public String getVender_status() {
                    return vender_status;
                }

                public void setVender_status(String vender_status) {
                    this.vender_status = vender_status;
                }

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

                public String getAddOnceDetailsId() {
                    return addOnceDetailsId;
                }

                public void setAddOnceDetailsId(String addOnceDetailsId) {
                    this.addOnceDetailsId = addOnceDetailsId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getRecommendedPrice() {
                    return recommendedPrice;
                }

                public void setRecommendedPrice(String recommendedPrice) {
                    this.recommendedPrice = recommendedPrice;
                }

                public String getRoleProduct() {
                    return roleProduct;
                }

                public void setRoleProduct(String roleProduct) {
                    this.roleProduct = roleProduct;
                }

                public String getMainProduct1() {
                    return mainProduct1;
                }

                public void setMainProduct1(String mainProduct1) {
                    this.mainProduct1 = mainProduct1;
                }

                public String getMainProduct2() {
                    return mainProduct2;
                }

                public void setMainProduct2(String mainProduct2) {
                    this.mainProduct2 = mainProduct2;
                }

                public String getMainProduct3() {
                    return mainProduct3;
                }

                public void setMainProduct3(String mainProduct3) {
                    this.mainProduct3 = mainProduct3;
                }

                public String getMaterialQty() {
                    return materialQty;
                }

                public void setMaterialQty(String materialQty) {
                    this.materialQty = materialQty;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

            }

        }

        public class ServiceId implements Serializable {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("add_once_details_id")
            @Expose
            private String addOnceDetailsId;
            @SerializedName("data")
            @Expose
            private Data__1 data;

            boolean isselected = false ;

            public boolean isIsselected() {
                return isselected;
            }

            public void setIsselected(boolean isselected) {
                this.isselected = isselected;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddOnceDetailsId() {
                return addOnceDetailsId;
            }

            public void setAddOnceDetailsId(String addOnceDetailsId) {
                this.addOnceDetailsId = addOnceDetailsId;
            }

            public Data__1 getData() {
                return data;
            }

            public void setData(Data__1 data) {
                this.data = data;
            }

            public class Data__1 implements Serializable{

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("add_once_details_id")
                @Expose
                private String addOnceDetailsId;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("image")
                @Expose
                private String image;
                @SerializedName("recommended_price")
                @Expose
                private String recommendedPrice;
                @SerializedName("service_type")
                @Expose
                private String serviceType;
                @SerializedName("main_service1")
                @Expose
                private String mainService1;
                @SerializedName("main_service2")
                @Expose
                private String mainService2;
                @SerializedName("main_service3")
                @Expose
                private String mainService3;
                @SerializedName("no_of_workers")
                @Expose
                private String noOfWorkers;
                @SerializedName("task_duration")
                @Expose
                private String taskDuration;
                @SerializedName("description")
                @Expose
                private String description;
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
                @SerializedName("vender_status")
                @Expose
                private String vender_status;

                boolean isselected = false ;

                public boolean isIsselected() {
                    return isselected;
                }

                public void setIsselected(boolean isselected) {
                    this.isselected = isselected;
                }

                public String getVender_status() {
                    return vender_status;
                }

                public void setVender_status(String vender_status) {
                    this.vender_status = vender_status;
                }

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

                public String getAddOnceDetailsId() {
                    return addOnceDetailsId;
                }

                public void setAddOnceDetailsId(String addOnceDetailsId) {
                    this.addOnceDetailsId = addOnceDetailsId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getRecommendedPrice() {
                    return recommendedPrice;
                }

                public void setRecommendedPrice(String recommendedPrice) {
                    this.recommendedPrice = recommendedPrice;
                }

                public String getServiceType() {
                    return serviceType;
                }

                public void setServiceType(String serviceType) {
                    this.serviceType = serviceType;
                }

                public String getMainService1() {
                    return mainService1;
                }

                public void setMainService1(String mainService1) {
                    this.mainService1 = mainService1;
                }

                public String getMainService2() {
                    return mainService2;
                }

                public void setMainService2(String mainService2) {
                    this.mainService2 = mainService2;
                }

                public String getMainService3() {
                    return mainService3;
                }

                public void setMainService3(String mainService3) {
                    this.mainService3 = mainService3;
                }

                public String getNoOfWorkers() {
                    return noOfWorkers;
                }

                public void setNoOfWorkers(String noOfWorkers) {
                    this.noOfWorkers = noOfWorkers;
                }

                public String getTaskDuration() {
                    return taskDuration;
                }

                public void setTaskDuration(String taskDuration) {
                    this.taskDuration = taskDuration;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

            }

        }

        public class ProductOfferDetailsId implements Serializable {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("offer_details_id")
            @Expose
            private String offerDetailsId;
            @SerializedName("data")
            @Expose
            private Data__4 data;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOfferDetailsId() {
                return offerDetailsId;
            }

            public void setOfferDetailsId(String offerDetailsId) {
                this.offerDetailsId = offerDetailsId;
            }

            public Data__4 getData() {
                return data;
            }

            public void setData(Data__4 data) {
                this.data = data;
            }

            public class Data__4 implements Serializable{

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("hindu_wedding_special")
                @Expose
                private String hinduWeddingSpecial;
                @SerializedName("offer_details_id")
                @Expose
                private String offerDetailsId;
                @SerializedName("product_name")
                @Expose
                private String productName;
                @SerializedName("image")
                @Expose
                private String image;
                @SerializedName("recommended_price")
                @Expose
                private String recommendedPrice;
                @SerializedName("role_product")
                @Expose
                private Object roleProduct;
                @SerializedName("main_product1")
                @Expose
                private String mainProduct1;
                @SerializedName("main_product2")
                @Expose
                private String mainProduct2;
                @SerializedName("main_product3")
                @Expose
                private String mainProduct3;
                @SerializedName("material_qty")
                @Expose
                private String materialQty;
                @SerializedName("unit")
                @Expose
                private String unit;
                @SerializedName("description")
                @Expose
                private String description;
                @SerializedName("created_at")
                @Expose
                private String createdAt;
                @SerializedName("count")
                @Expose
                private String count;
                @SerializedName("bid_max")
                @Expose
                private String bidMax;
                @SerializedName("bid_min")
                @Expose
                private String bidMin;
                @SerializedName("user_price")
                @Expose
                private String userPrice;
                @SerializedName("vender_status")
                @Expose
                private String vender_status;

                boolean isselected = false ;

                public boolean isIsselected() {
                    return isselected;
                }

                public void setIsselected(boolean isselected) {
                    this.isselected = isselected;
                }

                public String getVender_status() {
                    return vender_status;
                }

                public void setVender_status(String vender_status) {
                    this.vender_status = vender_status;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getHinduWeddingSpecial() {
                    return hinduWeddingSpecial;
                }

                public void setHinduWeddingSpecial(String hinduWeddingSpecial) {
                    this.hinduWeddingSpecial = hinduWeddingSpecial;
                }

                public String getOfferDetailsId() {
                    return offerDetailsId;
                }

                public void setOfferDetailsId(String offerDetailsId) {
                    this.offerDetailsId = offerDetailsId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getRecommendedPrice() {
                    return recommendedPrice;
                }

                public void setRecommendedPrice(String recommendedPrice) {
                    this.recommendedPrice = recommendedPrice;
                }

                public Object getRoleProduct() {
                    return roleProduct;
                }

                public void setRoleProduct(Object roleProduct) {
                    this.roleProduct = roleProduct;
                }

                public String getMainProduct1() {
                    return mainProduct1;
                }

                public void setMainProduct1(String mainProduct1) {
                    this.mainProduct1 = mainProduct1;
                }

                public String getMainProduct2() {
                    return mainProduct2;
                }

                public void setMainProduct2(String mainProduct2) {
                    this.mainProduct2 = mainProduct2;
                }

                public String getMainProduct3() {
                    return mainProduct3;
                }

                public void setMainProduct3(String mainProduct3) {
                    this.mainProduct3 = mainProduct3;
                }

                public String getMaterialQty() {
                    return materialQty;
                }

                public void setMaterialQty(String materialQty) {
                    this.materialQty = materialQty;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getBidMax() {
                    return bidMax;
                }

                public void setBidMax(String bidMax) {
                    this.bidMax = bidMax;
                }

                public String getBidMin() {
                    return bidMin;
                }

                public void setBidMin(String bidMin) {
                    this.bidMin = bidMin;
                }

                public String getUserPrice() {
                    return userPrice;
                }

                public void setUserPrice(String userPrice) {
                    this.userPrice = userPrice;
                }

            }

        }

        public class ServiceOfferDetailsId implements Serializable {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("offer_details_id")
            @Expose
            private String offerDetailsId;
            @SerializedName("data")
            @Expose
            private Data__3 data;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOfferDetailsId() {
                return offerDetailsId;
            }

            public void setOfferDetailsId(String offerDetailsId) {
                this.offerDetailsId = offerDetailsId;
            }

            public Data__3 getData() {
                return data;
            }

            public void setData(Data__3 data) {
                this.data = data;
            }


            public class Data__3 implements Serializable{

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("hindu_wedding_special")
                @Expose
                private String hinduWeddingSpecial;
                @SerializedName("offer_details_id")
                @Expose
                private String offerDetailsId;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("image")
                @Expose
                private String image;
                @SerializedName("recommended_price")
                @Expose
                private String recommendedPrice;
                @SerializedName("main_service1")
                @Expose
                private String mainService1;
                @SerializedName("main_service2")
                @Expose
                private String mainService2;
                @SerializedName("main_service3")
                @Expose
                private String mainService3;
                @SerializedName("no_of_workers")
                @Expose
                private String noOfWorkers;
                @SerializedName("task_duration")
                @Expose
                private String taskDuration;
                @SerializedName("description")
                @Expose
                private String description;
                @SerializedName("create_at")
                @Expose
                private String createAt;
                @SerializedName("count")
                @Expose
                private String count;
                @SerializedName("bid_max")
                @Expose
                private String bidMax;
                @SerializedName("bid_min")
                @Expose
                private String bidMin;
                @SerializedName("user_price")
                @Expose
                private String userPrice;
                @SerializedName("vender_status")
                @Expose
                private String vender_status;

                boolean isselected = false ;

                public boolean isIsselected() {
                    return isselected;
                }

                public void setIsselected(boolean isselected) {
                    this.isselected = isselected;
                }

                public String getVender_status() {
                    return vender_status;
                }

                public void setVender_status(String vender_status) {
                    this.vender_status = vender_status;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getHinduWeddingSpecial() {
                    return hinduWeddingSpecial;
                }

                public void setHinduWeddingSpecial(String hinduWeddingSpecial) {
                    this.hinduWeddingSpecial = hinduWeddingSpecial;
                }

                public String getOfferDetailsId() {
                    return offerDetailsId;
                }

                public void setOfferDetailsId(String offerDetailsId) {
                    this.offerDetailsId = offerDetailsId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getRecommendedPrice() {
                    return recommendedPrice;
                }

                public void setRecommendedPrice(String recommendedPrice) {
                    this.recommendedPrice = recommendedPrice;
                }

                public String getMainService1() {
                    return mainService1;
                }

                public void setMainService1(String mainService1) {
                    this.mainService1 = mainService1;
                }

                public String getMainService2() {
                    return mainService2;
                }

                public void setMainService2(String mainService2) {
                    this.mainService2 = mainService2;
                }

                public String getMainService3() {
                    return mainService3;
                }

                public void setMainService3(String mainService3) {
                    this.mainService3 = mainService3;
                }

                public String getNoOfWorkers() {
                    return noOfWorkers;
                }

                public void setNoOfWorkers(String noOfWorkers) {
                    this.noOfWorkers = noOfWorkers;
                }

                public String getTaskDuration() {
                    return taskDuration;
                }

                public void setTaskDuration(String taskDuration) {
                    this.taskDuration = taskDuration;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCreateAt() {
                    return createAt;
                }

                public void setCreateAt(String createAt) {
                    this.createAt = createAt;
                }

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getBidMax() {
                    return bidMax;
                }

                public void setBidMax(String bidMax) {
                    this.bidMax = bidMax;
                }

                public String getBidMin() {
                    return bidMin;
                }

                public void setBidMin(String bidMin) {
                    this.bidMin = bidMin;
                }

                public String getUserPrice() {
                    return userPrice;
                }

                public void setUserPrice(String userPrice) {
                    this.userPrice = userPrice;
                }

            }

        }

        public List<ServiceOfferDetailsId> getServiceOfferDetailsId() {
            return serviceOfferDetailsId;
        }

        public void setServiceOfferDetailsId(List<ServiceOfferDetailsId> serviceOfferDetailsId) {
            this.serviceOfferDetailsId = serviceOfferDetailsId;
        }

        public List<ProductOfferDetailsId> getProductOfferDetailsId() {
            return productOfferDetailsId;
        }

        public void setProductOfferDetailsId(List<ProductOfferDetailsId> productOfferDetailsId) {
            this.productOfferDetailsId = productOfferDetailsId;
        }
    }


}
