package com.Nuptist.calender.AddOnceNew;

import java.util.ArrayList;
import java.util.List;

public class OfferFinalModel {


    String title , id ;
    boolean isOpened ;

    List<FinalAddonsNewModel.FinalAddOnsData.ProductOfferDetailsId> productOfferDetailsIds = new ArrayList<>();
    List<FinalAddonsNewModel.FinalAddOnsData.ServiceOfferDetailsId> serviceOfferDetailsIds = new ArrayList<>();


    public OfferFinalModel(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public List<FinalAddonsNewModel.FinalAddOnsData.ProductOfferDetailsId> getProductOfferDetailsIds() {
        return productOfferDetailsIds;
    }

    public void setProductOfferDetailsIds(List<FinalAddonsNewModel.FinalAddOnsData.ProductOfferDetailsId> productOfferDetailsIds) {
        this.productOfferDetailsIds = productOfferDetailsIds;
    }

    public List<FinalAddonsNewModel.FinalAddOnsData.ServiceOfferDetailsId> getServiceOfferDetailsIds() {
        return serviceOfferDetailsIds;
    }

    public void setServiceOfferDetailsIds(List<FinalAddonsNewModel.FinalAddOnsData.ServiceOfferDetailsId> serviceOfferDetailsIds) {
        this.serviceOfferDetailsIds = serviceOfferDetailsIds;
    }


    @Override
    public String toString() {
        return "OfferFinalModel{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", isOpened=" + isOpened +
                ", productOfferDetailsIds=" + productOfferDetailsIds +
                ", serviceOfferDetailsIds=" + serviceOfferDetailsIds +
                '}';
    }
}
