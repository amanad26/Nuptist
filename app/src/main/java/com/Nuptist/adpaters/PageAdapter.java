package com.Nuptist.adpaters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Nuptist.Models.PackageDetailsModel;

public class PageAdapter extends FragmentStateAdapter {

    PackageDetailsModel data ;
    String id ;
    public PageAdapter(@NonNull FragmentActivity fragmentActivity,  PackageDetailsModel data  , String id ) {
        super(fragmentActivity);
        this.data = data;
        this.id = id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
