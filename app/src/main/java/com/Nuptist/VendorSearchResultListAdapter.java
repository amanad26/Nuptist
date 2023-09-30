package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VendorSearchResultListAdapter extends FragmentStateAdapter {



    public VendorSearchResultListAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new VendorAllSearchFragment();
        else if (position == 1) return new VendorLocalSearchFragment();
        else if (position == 2) return new VendorReceptionSearchFragment();
        else if (position == 3) return new VendorPre_WeedingFragment();
        else   return new VendorRegularWeedingFragment();

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
