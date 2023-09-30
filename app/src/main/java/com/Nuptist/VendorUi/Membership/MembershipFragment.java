package com.Nuptist.VendorUi.Membership;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Nuptist.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MembershipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembershipFragment extends Fragment {
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
   /* FragmentMyProfileBinding binding;
    MembershipFragmentBinding binding;*/
    TextView tv_1_1, tv_1_2, tv_1_3, tv_2_1, tv_2_2, tv_2_3, tv_3_1, tv_3_2, tv_3_3;
    LinearLayout card1, card2, card3;
    int currentPage = 0;
    Timer timer;
    int NUM_PAGES = 0;
    // creating object of ViewPager
    ViewPager mViewPager;


    // images array
    int[] images = {R.drawable.ic_setlocation, R.drawable.ic_liveactivity, R.drawable.ic_rattingsss, R.drawable.ic_world};
    ArrayList<String> list = new ArrayList<>();
    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter2 mViewPagerAdapter;
   /// DotsIndicator dotsIndicator;


    View root;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MembershipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MembershipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MembershipFragment newInstance(String param1, String param2) {
        MembershipFragment fragment = new MembershipFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_membership, container, false);

//
//        tv_1_1 = root.findViewById(R.id.tv_1_1);
//        tv_1_2 = root.findViewById(R.id.tv_1_2);
//        tv_1_3 = root.findViewById(R.id.tv_1_3);
//        tv_2_1 = root.findViewById(R.id.tv_2_1);
//        tv_2_2 = root.findViewById(R.id.tv_2_2);
//        tv_2_3 = root.findViewById(R.id.tv_2_3);
//        tv_3_1 = root.findViewById(R.id.tv_3_1);
//        tv_3_2 = root.findViewById(R.id.tv_3_2);
//        tv_3_3 = root.findViewById(R.id.tv_3_3);
//
//
//        card1 = root.findViewById(R.id.card1);
//        card2 = root.findViewById(R.id.card2);
//        card3 = root.findViewById(R.id.card3);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card1.setBackgroundResource(R.drawable.get_background);
                tv_1_1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_1_2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_1_3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_2_1.setTextColor(getResources().getColor(R.color.black));
                tv_2_2.setTextColor(getResources().getColor(R.color.black));
                tv_2_3.setTextColor(getResources().getColor(R.color.black));
                tv_3_1.setTextColor(getResources().getColor(R.color.black));
                tv_3_2.setTextColor(getResources().getColor(R.color.black));
                tv_3_3.setTextColor(getResources().getColor(R.color.black));


                card2.setBackgroundResource(R.drawable.edit_grey_background);
                card3.setBackgroundResource(R.drawable.edit_grey_background);

            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card2.setBackgroundResource(R.drawable.get_background);
                tv_1_1.setTextColor(getResources().getColor(R.color.black));
                tv_1_2.setTextColor(getResources().getColor(R.color.black));
                tv_1_3.setTextColor(getResources().getColor(R.color.black));
                tv_2_1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_2_2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_2_3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_3_1.setTextColor(getResources().getColor(R.color.black));
                tv_3_2.setTextColor(getResources().getColor(R.color.black));
                tv_3_3.setTextColor(getResources().getColor(R.color.black));

                card1.setBackgroundResource(R.drawable.edit_grey_background);
                card3.setBackgroundResource(R.drawable.edit_grey_background);

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card3.setBackgroundResource(R.drawable.get_background);
                tv_1_1.setTextColor(getResources().getColor(R.color.black));
                tv_1_2.setTextColor(getResources().getColor(R.color.black));
                tv_1_3.setTextColor(getResources().getColor(R.color.black));
                tv_2_1.setTextColor(getResources().getColor(R.color.black));
                tv_2_2.setTextColor(getResources().getColor(R.color.black));
                tv_2_3.setTextColor(getResources().getColor(R.color.black));
                tv_3_1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_3_2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_3_3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                card2.setBackgroundResource(R.drawable.edit_grey_background);
                card1.setBackgroundResource(R.drawable.edit_grey_background);

            }
        });
//
//        // Initializing the ViewPager Object
//        mViewPager = root.findViewById(R.id.viewPagerMain);
//
//
//        // for dot indicator
//
//        dotsIndicator = root.findViewById(R.id.dots_indicator);
//        // viewPager = (ViewPager) findViewById(R.id.view_pager);
//        //adapter = new ViewPagerAdapter();
//        //viewPager.setAdapter(adapter);
//

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter2(getActivity(), images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
        // for dot indicator


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
     /////   dotsIndicator.setViewPager(mViewPager);



        return root;
    }
}