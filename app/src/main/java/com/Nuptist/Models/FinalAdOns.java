package com.Nuptist.Models;

import java.util.ArrayList;
import java.util.List;

public class FinalAdOns {
    boolean isOpened = false ;
    String name, addOnsId;

    public List<AddOncesModelNew.AddOnceData.AllAddOnces.PackageDataInAddonce> adOnsList = new ArrayList<>();


    public String getAddOnsId() {
        return addOnsId;
    }

    public void setAddOnsId(String addOnsId) {
        this.addOnsId = addOnsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AddOncesModelNew.AddOnceData.AllAddOnces.PackageDataInAddonce> getAdOnsList() {
        return adOnsList;
    }

    public void setAdOnsList(List<AddOncesModelNew.AddOnceData.AllAddOnces.PackageDataInAddonce> adOnsList) {
        this.adOnsList = adOnsList;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }
}
