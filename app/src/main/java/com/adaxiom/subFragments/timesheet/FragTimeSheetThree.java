package com.adaxiom.subFragments.timesheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.adaxiom.locumset.R;
import com.adaxiom.locumset.TimeSheet;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.adaxiom.utils.Constants.PREF_BREAK_TIME;
import static com.adaxiom.utils.Constants.PREF_TOTAL_AMOUNT;


public class FragTimeSheetThree extends Fragment {

    View view;

    TextView tvTotalAmount, tvTotalAmountDisable;
    EditText etBreakMin;
    String totalAmount="";
    String breakTime = "30";
    TimeSheet timeSheet;
    public static int total_amount=0;

    int stime = 0, etime = 0, pri = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_timesheet_three, container, false);

        setViews();

        return view;
    }

    private void setViews() {

        tvTotalAmount = view.findViewById(R.id.tvTotalAmountFragThree);
        tvTotalAmountDisable = view.findViewById(R.id.tvTotalAmountDisableFragThree);
        etBreakMin = view.findViewById(R.id.etBreakMinFragThree);


        Prefs.putString(PREF_TOTAL_AMOUNT, totalAmount);
        Prefs.putString(PREF_BREAK_TIME, breakTime);


        tvTotalAmount.setText("£"+timeSheet.total_amount+"");
        tvTotalAmountDisable.setText("£"+timeSheet.total_amount+"");



    }





}
