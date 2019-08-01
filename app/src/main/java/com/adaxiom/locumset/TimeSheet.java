package com.adaxiom.locumset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import devmike.jade.com.PageStepIndicator;

public class TimeSheet extends AppCompatActivity {


    PageStepIndicator pageStepper;
    public static ViewPager viewPager;
    public static int int_items = 2;
    Button btnNext, btnPre;
    public static String imagePath="";

    public static String strTitle, strPrice, strDep, strAdd, strFromDate,
            strStartTime, strJobId, strNote, strEndTime, strFlag,
            strToDate, strPayGrade, strGrade, strEmail, strPhone;

    public static int jobId = 0, intStatus = 0, total_amount=0, pri=0;


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



        calculateTotalAmount(strStartTime, strEndTime);

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

//                Toast.makeText(TimeSheet.this, viewPager.getCurrentItem()+"",Toast.LENGTH_SHORT).show();
                if(viewPager.getCurrentItem()+1 == 4){
                    btnNext.setText("Done");
                }

                if(viewPager.getCurrentItem() == 4){
                    Toast.makeText(TimeSheet.this, "Done Click", Toast.LENGTH_SHORT).show();
                }


                viewPager.setCurrentItem(getItem(+1), true);
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    btnNext.setText("Next");
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


    public static void setImagePath(String path){
        imagePath = path;
    }

    public static String getImagePath(){
        return imagePath;
    }





    public void calculateTotalAmount(String timeOne, String timeTwo){


//        if (!strPrice.equalsIgnoreCase("")) {
            try {
//                stime = Integer.parseInt(sTime);
//                etime = Integer.parseInt(eTime);
                pri = Integer.parseInt(strPrice);
            }catch (Exception e){
                Log.e("Number formate", e.toString());
                e.printStackTrace();
            }
//        }




        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date1 = null, date2 = null;
        try {
            date1 = simpleDateFormat.parse(timeOne);
            date2 = simpleDateFormat.parse(timeTwo);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NumberFormatException num){
            num.printStackTrace();
        }

        if(timeOne.endsWith("AM")){
            Log.e("True","true");
        }

        long difference = date1.getTime() - date2.getTime();
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        if(days > 0){
            hours = hours + 12;
        }
        Log.i("mHours", " :: " + hours);


        total_amount = hours * pri;


    }




}
