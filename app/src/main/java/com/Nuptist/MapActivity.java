package com.Nuptist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.Session.Session;
import com.Nuptist.frags.VenueMapFragment;
import com.Nuptist.mainscreens.MapFragment;
import com.Nuptist.venue.VenueActivity;

public class MapActivity extends AppCompatActivity implements MapInterface {

    TextView address_selectecd ,create_venue_btn;
    Session session ;
    private String address_new , lat2 , lang2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        address_selectecd = findViewById(R.id.address_selectecd);
        create_venue_btn = findViewById(R.id.create_venue_btn);

        session = new Session(MapActivity.this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, new MapFragment(MapActivity.this));
        transaction.commit();

        create_venue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setAddress(address_new);
                session.setLat(lat2);
                session.setLang(lang2);
               startActivity(new Intent(MapActivity.this,HomeActivity.class).putExtra("address",address_new));
            }
        });


    }

    @Override
    public void getAddress(double lat, double lang, String address) {

        address_selectecd.setText(address);
        address_new  = address ;
        lang2 = String.valueOf(lang);
        lat2 = String.valueOf(lat);

    }
}