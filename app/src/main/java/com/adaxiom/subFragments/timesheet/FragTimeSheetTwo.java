package com.adaxiom.subFragments.timesheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.adaxiom.locumset.R;
import com.adaxiom.locumset.TimeSheet;


public class FragTimeSheetTwo extends Fragment {

    View view;

    TextView tvGrade, tvPrice;
    EditText etStartTime, etEndTime;

    TimeSheet timeSheet;

    public static int totalAmount=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_timesheet_two, container, false);

        setViews();

        return view;
    }


    private void setViews(){

        tvGrade = view. findViewById(R.id.tvGradeFragTwo);
        tvPrice = view. findViewById(R.id.tvPriceFragTwo);
        etStartTime = view.findViewById(R.id.etStartTimeFragTwo);
        etEndTime = view.findViewById(R.id.etEndTimeFragTwo);

        tvGrade.setText(timeSheet.strGrade);
        tvPrice.setText("£"+timeSheet.strPrice);
        etStartTime.setText(timeSheet.strStartTime);
        etEndTime.setText(timeSheet.strEndTime);


    }

}
