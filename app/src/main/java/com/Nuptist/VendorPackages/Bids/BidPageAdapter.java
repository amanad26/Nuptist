package com.Nuptist.VendorPackages.Bids;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BidPageAdapter extends FragmentStateAdapter {
    public BidPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      if(position == 0) return  new MyAllBidsFragment();
      else return  new MyAcceptedBidsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
