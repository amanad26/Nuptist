package com.Nuptist.Models.BookingModels;

import java.io.Serializable;

public class BookingOffersModel implements Serializable {

    String offerId , offerPrice , offerName , product_id , service_id ,vendor_id , vendor_name ;
    int id ;

    public BookingOffersModel(String offerId, String offerPrice, String offerName) {
        this.offerId = offerId;
        this.offerPrice = offerPrice;
        this.offerName = offerName;
    }

    public BookingOffersModel(String offerId, String offerPrice, String offerName, String product_id, String service_id, String vendor_id ,String vendor_name) {
        this.offerId = offerId;
        this.offerPrice = offerPrice;
        this.offerName = offerName;
        this.product_id = product_id;
        this.service_id = service_id;
        this.vendor_id =  vendor_id ;
        this.vendor_name =  vendor_name ;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "BookingOffersModel{" +
                "offerId='" + offerId + '\'' +
                ", offerPrice='" + offerPrice + '\'' +
                ", offerName='" + offerName + '\'' +
                ", product_id='" + product_id + '\'' +
                ", service_id='" + service_id + '\'' +
                ", vendor_id='" + vendor_id + '\'' +
                ", vendor_name='" + vendor_name + '\'' +
                ", id=" + id +
                '}';
    }
}
