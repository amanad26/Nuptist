package com.Nuptist.adpaters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Nuptist.VendorHomeEventsFragment;
import com.Nuptist.VendorHomePackagesFragment;

public class VendorHomePageAdapter extends FragmentStateAdapter {


    public VendorHomePageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if (position == 0) return  new VendorHomePackagesFragment();
       else return new VendorHomeEventsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
