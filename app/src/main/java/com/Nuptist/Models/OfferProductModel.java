package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferProductModel {


    @SerializedName("result")
    @Expose
    private String result;

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

    public OfferProductModel.OfferProductData getOfferProductData() {
        return OfferProductData;
    }

    public void setOfferProductData(OfferProductModel.OfferProductData offerProductData) {
        OfferProductData = offerProductData;
    }

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private OfferProductData OfferProductData = null ;


    public  class  OfferProductData{

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

}
