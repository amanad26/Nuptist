package com.Nuptist.Models.BookingModels;

import java.io.Serializable;

public class BookingAddOnceModel implements Serializable {

    String id , addOnceId , addOnceName , addOnceMinPrice , addOnceMaPrice , product_id , service_id, vendor_id ,vendor_name;


    public BookingAddOnceModel( String addOnceId, String addOnceName, String addOnceMinPrice, String addOnceMaPrice) {
        this.addOnceId = addOnceId;
        this.addOnceName = addOnceName;
        this.addOnceMinPrice = addOnceMinPrice;
        this.addOnceMaPrice = addOnceMaPrice;
    }

    public BookingAddOnceModel(String addOnceId, String addOnceName, String addOnceMinPrice, String addOnceMaPrice, String product_id, String service_id, String vendor_id, String vendor_name) {
        this.addOnceId = addOnceId;
        this.addOnceName = addOnceName;
        this.addOnceMinPrice = addOnceMinPrice;
        this.addOnceMaPrice = addOnceMaPrice;
        this.product_id = product_id;
        this.service_id = service_id;
        this.vendor_id = vendor_id ;
        this.vendor_name = vendor_name ;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddOnceId() {
        return addOnceId;
    }

    public void setAddOnceId(String addOnceId) {
        this.addOnceId = addOnceId;
    }

    public String getAddOnceName() {
        return addOnceName;
    }

    public void setAddOnceName(String addOnceName) {
        this.addOnceName = addOnceName;
    }

    public String getAddOnceMinPrice() {
        return addOnceMinPrice;
    }

    public void setAddOnceMinPrice(String addOnceMinPrice) {
        this.addOnceMinPrice = addOnceMinPrice;
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

    public String getAddOnceMaPrice() {
        return addOnceMaPrice;
    }

    public void setAddOnceMaPrice(String addOnceMaPrice) {
        this.addOnceMaPrice = addOnceMaPrice;
    }


    @Override
    public String toString() {
        return "BookingAddOnceModel{" +
                "id='" + id + '\'' +
                ", addOnceId='" + addOnceId + '\'' +
                ", addOnceName='" + addOnceName + '\'' +
                ", addOnceMinPrice='" + addOnceMinPrice + '\'' +
                ", addOnceMaPrice='" + addOnceMaPrice + '\'' +
                ", product_id='" + product_id + '\'' +
                ", service_id='" + service_id + '\'' +
                ", vendor_id='" + vendor_id + '\'' +
                ", vendor_name='" + vendor_name + '\'' +
                '}';
    }
}
