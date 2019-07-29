package com.adaxiom.subFragments.timesheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.adaxiom.locumset.R;


public class FragTimeSheetThree extends Fragment {

    View view;

    TextView tvTotalAmount, tvTotalAmountDisable;
    EditText etBreakMin;

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

        tvTotalAmount.setText("£540");

    }

}
