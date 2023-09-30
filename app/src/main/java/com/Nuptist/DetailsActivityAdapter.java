package com.Nuptist;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Nuptist.Models.PackageDetailsModel;

import java.util.logging.LogManager;

public class DetailsActivityAdapter extends FragmentStateAdapter {


    PackageDetailsModel data ;
    String id ;

    public DetailsActivityAdapter(@NonNull FragmentActivity fragmentActivity ,   PackageDetailsModel  data, String id ) {
        super(fragmentActivity);
        this.data = data ;
        this.id = id ;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Log.e("TAG", "createFragment() called with: position = [" + id + "]");

//        if (position == 0)
//        return new VenueFragment(data);
//        else if (position == 1) return new OfferFragment(data);
//        else   return new AddOnceFragment(data);

        return  new AddOnceFragment(data) ;
    }



    @Override
    public int getItemCount() {
        return 3;
    }
}
