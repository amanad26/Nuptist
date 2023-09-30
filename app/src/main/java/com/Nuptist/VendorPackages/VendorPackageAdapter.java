package com.Nuptist.VendorPackages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Nuptist.AddOnceFragment;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.OfferFragment;
import com.Nuptist.RetrofitApis.BidInterface;
import com.Nuptist.VenueFragment;

public class VendorPackageAdapter extends FragmentStateAdapter {

    PackageDetailsModel data ;
    String id ;
    BidInterface bidInterface ;
    public VendorPackageAdapter(@NonNull FragmentActivity fragmentActivity,  PackageDetailsModel data  , String id, BidInterface bidInterface ) {
        super(fragmentActivity);
        this.data = data;
        this.id = id;
        this.bidInterface = bidInterface;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        if (position == 0)
//            return new VenueFragment(data);
//        else if (position == 1) return new OfferFragment(data);
//        else   return new VendorAddonceFragment(data,bidInterface);
        return new VendorAddonceFragment(data,bidInterface);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
