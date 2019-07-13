package com.adaxiom.subFragments.timesheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adaxiom.locumset.R;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;


public class FragTimeSheetOne extends Fragment {

    View view;
    private ScrollChoice scrollChoice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_timesheet_one, container, false);

        setViews();

        return view;
    }


    public void setViews(){

        scrollChoice = view.findViewById(R.id.scroll_choice);

        List<String> data = new ArrayList<>();
        data.add("Brazil");
        data.add("USA");
        data.add("China");
        data.add("Pakistan");
        data.add("Australia");
        data.add("India");
        data.add("Nepal");
        data.add("Sri Lanka");
        data.add("Spain");
        data.add("Italy");
        data.add("France");


        scrollChoice.addItems(data,5);
    }


}
