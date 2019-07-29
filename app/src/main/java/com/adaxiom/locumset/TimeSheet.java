package com.adaxiom.locumset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    Button btnNext, btnPre;

    public static String strTitle, strPrice, strDep, strAdd, strFromDate,
            strStartTime, strJobId, strNote, strEndTime, strFlag,
            strToDate, strPayGrade, strGrade, strEmail, strPhone;

    public static int jobId = 0, intStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);

        setTitle("Time Sheet");
        getData();
        setViews();

    }



    public void getData(){

        strTitle = getIntent().getStringExtra("Title");
        strPrice = getIntent().getStringExtra("Price");
        strDep = getIntent().getStringExtra("Dep");
        strAdd = getIntent().getStringExtra("Add");
        strFromDate = getIntent().getStringExtra("fDate");
        strToDate = getIntent().getStringExtra("tDate");
        strStartTime = getIntent().getStringExtra("sTime");
        strEndTime = getIntent().getStringExtra("eTime");
        jobId = getIntent().getIntExtra("jobId", 0);
        strNote = getIntent().getStringExtra("Note");
        strFlag = getIntent().getStringExtra("flag");
        strPayGrade = getIntent().getStringExtra("payGrade");
        strGrade = getIntent().getStringExtra("Grade");
        strEmail = getIntent().getStringExtra("Email");
        strPhone = getIntent().getStringExtra("Phone");
        intStatus = getIntent().getIntExtra("status", 0);

    }


    private void setViews(){

        btnNext = findViewById(R.id.btnNextViewPager);
        btnPre = findViewById(R.id.btnPreviousViewPager);

        pageStepper = findViewById(R.id.page_stepper);
        viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        pageStepper.setupWithViewPager(viewPager);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(getItem(+1), true);
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(-1), true);
            }
        });

    }


    private int getItem(int i){
        return viewPager.getCurrentItem() + i;
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
