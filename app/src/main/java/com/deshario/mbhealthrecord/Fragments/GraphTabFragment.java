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

import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.R;


/**
 * A simple {@link Fragment} subclass.
 */

public class GraphTabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;
    MyAdapter myAdapter;

    final int[] ICONS = new int[] {
            R.drawable.ic_timeline_white_24dp,
            R.drawable.ic_add_circle_white_24dp,
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate tab_layout and setup Views
        View myview =  inflater.inflate(R.layout.bottom_navigation,null);
        ((MainActivity) getActivity()).setActionBarTitle("บันทึกสุขภาพประจำสัปดาห์");
        tabLayout = (TabLayout) myview.findViewById(R.id.tabs);
        viewPager = (ViewPager) myview.findViewById(R.id.viewpager);
        adap();

        return myview;
    }

    public void adap(){
        //Set an Apater for the View Pager
        myAdapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(myAdapter);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    myAdapter.notifyDataSetChanged();
                    tabLayout.getTabAt(0).setIcon(ICONS[0]);
                    tabLayout.getTabAt(1).setIcon(ICONS[1]);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // SetupWithViewPager doesn't works without the runnable
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(ICONS[0]);
                tabLayout.getTabAt(1).setIcon(ICONS[1]);
            }
        });

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
                case 0 : return new GraphFragment();
                case 1 : return new GraphDataFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public int getItemPosition(Object object) { // Notify changed
            // Causes adapter to reload all Fragments when
            // notifyDataSetChanged is called
            return POSITION_NONE;
        }

        // Returns the title of the tab according to the position.
       @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "กราฟโภชนาการ";
                case 1 :
                    return "บันทึกน้ำหนัก";
            }
            return null;
        }

    }

}



