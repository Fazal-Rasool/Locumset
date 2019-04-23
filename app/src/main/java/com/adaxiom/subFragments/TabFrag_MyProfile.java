package com.adaxiom.subFragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adaxiom.locumset.R;

public class TabFrag_MyProfile extends Fragment {

    View view;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab_frag__my_profile, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabsMyProfile);
        viewPager = (ViewPager) view.findViewById(R.id.pagerMyProfile);


        viewPager.setAdapter(new AdapterMyProfileTabs(getChildFragmentManager()));


        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        viewPager.setCurrentItem(0);


        return view;
    }

    class AdapterMyProfileTabs extends FragmentPagerAdapter {

        public AdapterMyProfileTabs(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragMyDetails();
                case 1:
                    return new FragMyDocs();
                case 2:
                    return new FragNotification();
                case 3:
                    return new FragSettings();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "My Details";
                case 1:
                    return "My Docs";
                case 2:
                    return "Notification";
                case 3:
                    return "Settings";
            }
            return null;
        }

    }



}
