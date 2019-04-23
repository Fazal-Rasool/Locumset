package com.adaxiom.subFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adaxiom.locumset.R;


public class FragMyDocs extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_my_documents, container, false);


        createObjects();
        setViews();


        return view;
    }


    public void createObjects(){

    }

    public void setViews(){

    }

}
