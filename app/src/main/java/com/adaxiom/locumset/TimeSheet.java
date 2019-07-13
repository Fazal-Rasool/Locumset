package com.adaxiom.locumset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adaxiom.fragments.FragAllJobs;
import com.adaxiom.fragments.FragMyProfile;
import com.adaxiom.fragments.FragMyShifts;
import com.adaxiom.fragments.TabFragment;
import com.adaxiom.subFragments.FragSettings;
import com.adaxiom.subFragments.timesheet.FragTimeSheetFive;
import com.adaxiom.subFragments.timesheet.FragTimeSheetFour;
import com.adaxiom.subFragments.timesheet.FragTimeSheetOne;
import com.adaxiom.subFragments.timesheet.FragTimeSheetThree;
import com.adaxiom.subFragments.timesheet.FragTimeSheetTwo;

import devmike.jade.com.PageStepIndicator;

public class TimeSheet extends AppCompatActivity {


    PageStepIndicator pageStepper;
    public static ViewPager viewPager;
    public static int int_items = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);

        setTitle("Time Sheet");
        setViews();

    }


    private void setViews(){

        pageStepper = findViewById(R.id.page_stepper);
        viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        pageStepper.setupWithViewPager(viewPager);

    }


    class MyPagerAdapter extends FragmentStatePagerAdapter

    {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FragTimeSheetOne();
                case 1:
                    return new FragTimeSheetTwo();
                case 2:
                    return new FragTimeSheetThree();
                case 3:
                    return new FragTimeSheetFour();
                case 4:
                    return new FragTimeSheetFive();
            }

            return  null;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }



}
