package com.Nuptist.VendorPackages.Bids.OffersBid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OfferBidsPageAdapter extends FragmentStateAdapter {
    public OfferBidsPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position == 0 ) return  new OffersAllBidsFragment();
       else return  new OfferAcceptedBidFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
