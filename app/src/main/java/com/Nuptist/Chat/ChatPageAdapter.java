package com.Nuptist.Chat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Nuptist.Chat.ChatWithUsers.ChatVendorWithUserFragment;

public class ChatPageAdapter extends FragmentStateAdapter {

    String type ;



    public ChatPageAdapter(@NonNull FragmentActivity fragmentActivity,String type ) {
        super(fragmentActivity);
        this.type = type ;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position == 0 ){
           if(type.equalsIgnoreCase("user"))
           return  new ChatWithVendorsFragment();
           else  return  new ChatVendorWithUserFragment();

       }
       else  return new ChatWithAdminFragment();





    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
