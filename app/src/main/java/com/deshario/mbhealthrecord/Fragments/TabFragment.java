package com.deshario.mbhealthrecord.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deshario.mbhealthrecord.Fragments.Quarter_Month1;
import com.deshario.mbhealthrecord.Fragments.Quarter_Month2;
import com.deshario.mbhealthrecord.Fragments.Quarter_Month3;
import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.R;


/**
 * A simple {@link Fragment} subclass.
 */

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate tab_layout and setup Views
        View myview =  inflater.inflate(R.layout.tab_layout,null);
        ((MainActivity) getActivity()).setActionBarTitle("พัฒนาการของลูกในครรภ์");
        tabLayout = (TabLayout) myview.findViewById(R.id.tabs);
        viewPager = (ViewPager) myview.findViewById(R.id.viewpager);

        //Set an Apater for the View Pager
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        final int[] ICONS = new int[] {
                R.drawable.ic_filter_1_white_24dp,
                R.drawable.ic_filter_2_white_24dp,
                R.drawable.ic_filter_3_white_24dp,
        };


        // SetupWithViewPager doesn't works without the runnable
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(ICONS[0]);
                tabLayout.getTabAt(1).setIcon(ICONS[1]);
                tabLayout.getTabAt(2).setIcon(ICONS[2]);
            }
        });
        return myview;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        // Return fragment with respect to Position
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new Quarter_Month1();
                case 1 : return new Quarter_Month2();
                case 2 : return new Quarter_Month3();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        // Returns the title of the tab according to the position.
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "ไตรมาสที่ 1";
                case 1 :
                    return "ไตรมาสที่ 2";
                case 2 :
                    return "ไตรมาสที่ 3";
            }
            return null;
        }
    }

}


