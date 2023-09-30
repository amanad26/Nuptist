package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class VendorSearchResultActivity extends AppCompatActivity {

    TabLayout tab_lay;
    ImageView ic_back;


    private final String[] titles = new String[]{"All", "Local", "Reception", "Pre-wedding","Regional"};
    private ViewPager2 product_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_search_result);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));




        ic_back = findViewById(R.id.ic_back);
        product_viewpager = findViewById(R.id.product_viewpager);

        product_viewpager.setAdapter(new VendorSearchResultListAdapter(VendorSearchResultActivity.this));
        tab_lay = findViewById(R.id.tab_lay);
        tab_lay.setTabMode(TabLayout.MODE_SCROLLABLE);





        new TabLayoutMediator(tab_lay, product_viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
                //  tab.setIcon(mTabIconsSelected[position]);
            }
        }).attach();


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}