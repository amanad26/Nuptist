package com.Nuptist.MyBookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyBookingDetailsModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MyBookingdata data;

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

    public MyBookingdata getData() {
        return data;
    }

    public void setData(MyBookingdata data) {
        this.data = data;
    }

    public class MyBookingdata{


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
        @SerializedName("service_id")
        @Expose
        private List<ServiceIdList> serviceId = null;
        @SerializedName("product_id")
        @Expose
        private List<ProductIdList> productId = null;
        @SerializedName("service_offer_details_id")
        @Expose
        private List<ServiceOfferIdList> serviceOfferDetailsId = null;
        @SerializedName("product_offer_details_id")
        @Expose
        private List<ProductOfferIdList> productOfferDetailsId = null;
        @SerializedName("bids_addonce")
        @Expose
        private List<BidsAddonce> bidsAddonce = null;
        @SerializedName("bids_offer")
        @Expose
        private List<BidsOffer> bidsOffer = null;
        @SerializedName("bids_id")
        @Expose
        private String bidsId;
        @SerializedName("package_name")
        @Expose
        private String packageName;
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
        @SerializedName("review_status")
        @Expose
        private String reviewStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public List<ServiceIdList> getServiceId() {
            return serviceId;
        }

        public void setServiceId(List<ServiceIdList> serviceId) {
            this.serviceId = serviceId;
        }

        public List<ProductIdList> getProductId() {
            return productId;
        }

        public void setProductId(List<ProductIdList> productId) {
            this.productId = productId;
        }

        public List<ServiceOfferIdList> getServiceOfferDetailsId() {
            return serviceOfferDetailsId;
        }

        public void setServiceOfferDetailsId(List<ServiceOfferIdList> serviceOfferDetailsId) {
            this.serviceOfferDetailsId = serviceOfferDetailsId;
        }

        public List<ProductOfferIdList> getProductOfferDetailsId() {
            return productOfferDetailsId;
        }

        public void setProductOfferDetailsId(List<ProductOfferIdList> productOfferDetailsId) {
            this.productOfferDetailsId = productOfferDetailsId;
        }

        public List<BidsAddonce> getBidsAddonce() {
            return bidsAddonce;
        }

        public void setBidsAddonce(List<BidsAddonce> bidsAddonce) {
            this.bidsAddonce = bidsAddonce;
        }

        public List<BidsOffer> getBidsOffer() {
            return bidsOffer;
        }

        public void setBidsOffer(List<BidsOffer> bidsOffer) {
            this.bidsOffer = bidsOffer;
        }

        public String getBidsId() {
            return bidsId;
        }

        public void setBidsId(String bidsId) {
            this.bidsId = bidsId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
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

        public String getReviewStatus() {
            return reviewStatus;
        }

        public void setReviewStatus(String reviewStatus) {
            this.reviewStatus = reviewStatus;
        }



        public  class  ProductIdList{


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
            @SerializedName("vender_status")
            @Expose
            private String venderStatus;

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

            public String getVenderStatus() {
                return venderStatus;
            }

            public void setVenderStatus(String venderStatus) {
                this.venderStatus = venderStatus;
            }



        }


        public class ProductOfferIdList{
            @SerializedName("id")
            @Expose
            private String id;
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
            @SerializedName("vender_status")
            @Expose
            private String venderStatus;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getVenderStatus() {
                return venderStatus;
            }

            public void setVenderStatus(String venderStatus) {
                this.venderStatus = venderStatus;
            }

        }

        public  class  ServiceIdList{

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
            @SerializedName("vender_status")
            @Expose
            private String venderStatus;

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

            public String getVenderStatus() {
                return venderStatus;
            }

            public void setVenderStatus(String venderStatus) {
                this.venderStatus = venderStatus;
            }

        }

        public  class  ServiceOfferIdList{


            @SerializedName("id")
            @Expose
            private String id;
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
            @SerializedName("vender_status")
            @Expose
            private String venderStatus;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getVenderStatus() {
                return venderStatus;
            }

            public void setVenderStatus(String venderStatus) {
                this.venderStatus = venderStatus;
            }

        }


        public class BidsAddonce {

            @SerializedName("bids_id")
            @Expose
            private String bidsId;
            @SerializedName("bids_price")
            @Expose
            private String bidsPrice;
            @SerializedName("vender_id")
            @Expose
            private String venderId;
            @SerializedName("vender_name")
            @Expose
            private String venderName;
            @SerializedName("vender_image")
            @Expose
            private String venderImage;
            @SerializedName("addonce_id")
            @Expose
            private String addonceId;
            @SerializedName("addonce_name")
            @Expose
            private String addonceName;

            public String getBidsId() {
                return bidsId;
            }

            public void setBidsId(String bidsId) {
                this.bidsId = bidsId;
            }

            public String getBidsPrice() {
                return bidsPrice;
            }

            public void setBidsPrice(String bidsPrice) {
                this.bidsPrice = bidsPrice;
            }

            public String getVenderId() {
                return venderId;
            }

            public void setVenderId(String venderId) {
                this.venderId = venderId;
            }

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

            public String getAddonceId() {
                return addonceId;
            }

            public void setAddonceId(String addonceId) {
                this.addonceId = addonceId;
            }

            public String getAddonceName() {
                return addonceName;
            }

            public void setAddonceName(String addonceName) {
                this.addonceName = addonceName;
            }

        }

        public class BidsOffer {

            @Override
            public String toString() {
                return "BidsOffer{" +
                        "bidsId='" + bidsId + '\'' +
                        ", bidsPrice='" + bidsPrice + '\'' +
                        ", venderId='" + venderId + '\'' +
                        ", venderName='" + venderName + '\'' +
                        ", venderImage='" + venderImage + '\'' +
                        ", offerId='" + offerId + '\'' +
                        ", offerName='" + offerName + '\'' +
                        '}';
            }

            @SerializedName("bids_id")
            @Expose
            private String bidsId;
            @SerializedName("bids_price")
            @Expose
            private String bidsPrice;
            @SerializedName("vender_id")
            @Expose
            private String venderId;
            @SerializedName("vender_name")
            @Expose
            private String venderName;
            @SerializedName("vender_image")
            @Expose
            private String venderImage;
            @SerializedName("offer_id")
            @Expose
            private String offerId;
            @SerializedName("offer_name")
            @Expose
            private String offerName;

            public String getBidsId() {
                return bidsId;
            }

            public void setBidsId(String bidsId) {
                this.bidsId = bidsId;
            }

            public String getBidsPrice() {
                return bidsPrice;
            }

            public void setBidsPrice(String bidsPrice) {
                this.bidsPrice = bidsPrice;
            }

            public String getVenderId() {
                return venderId;
            }

            public void setVenderId(String venderId) {
                this.venderId = venderId;
            }

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

            public String getOfferId() {
                return offerId;
            }

            public void setOfferId(String offerId) {
                this.offerId = offerId;
            }

            public String getOfferName() {
                return offerName;
            }

            public void setOfferName(String offerName) {
                this.offerName = offerName;
            }

        }

    }



}
