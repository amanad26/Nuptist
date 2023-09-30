package com.Nuptist.calender.AddOnceNew;

import java.util.ArrayList;
import java.util.List;

public class FilterdFinalAddonsModel {

    String title , id ;
    boolean isOpened ;
    List<FinalAddonsNewModel.FinalAddOnsData.ProductId> productIds = new ArrayList<>();
    List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> serviceIdList = new ArrayList<>();

    public FilterdFinalAddonsModel(String title, String id) {
        this.title = title;
        this.id = id;
    }


    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
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

    public List<FinalAddonsNewModel.FinalAddOnsData.ProductId> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<FinalAddonsNewModel.FinalAddOnsData.ProductId> productIds) {
        this.productIds = productIds;
    }

    public List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> serviceIdList) {
        this.serviceIdList = serviceIdList;
    }

    @Override
    public String toString() {
        return "FilterdFinalAddonsModel{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", isOpened=" + isOpened +
                ", productIds=" + productIds +
                ", serviceIdList=" + serviceIdList +
                '}';
    }
}
