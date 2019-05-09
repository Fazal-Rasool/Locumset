package com.adaxiom.subFragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.adaxiom.database.DatabaseHelper;
import com.adaxiom.locumset.R;
import com.adaxiom.models.ModelUser;
import com.adaxiom.utils.SharedPrefrence;


public class FragMyDetails extends Fragment {

    View view;
    FloatingActionButton btnEdit, btnSave;
    EditText etName, etGmc, etEmail, etPhone;
    DatabaseHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_my_details, container, false);

        createObjects();
        setViews();
        setListeners();
        getDataFromDB();
        disableFields();


        return view;
    }



    public void createObjects() {
        dbHelper = DatabaseHelper.getInstance();
    }

    public void setViews() {

        etName = (EditText) view.findViewById(R.id.etMyDetailName);
        etGmc = (EditText) view.findViewById(R.id.etMyDetailGmc);
        etEmail = (EditText) view.findViewById(R.id.etMyDetailEmail);
        etPhone = (EditText) view.findViewById(R.id.etMyDetailPhone);

        btnEdit = (FloatingActionButton) view.findViewById(R.id.btnMyDetailEdit);
        btnSave = (FloatingActionButton) view.findViewById(R.id.btnMyDetailSave);

    }


    public void setListeners(){

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableFields();
                etName.setCursorVisible(true);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableFields();
            }
        });

    }


    private void getDataFromDB() {
        int userId = SharedPrefrence.getUserId(getActivity());
        ModelUser model =dbHelper.getUserList(userId);
        String email = model.userEmail;
        String GmcNum = model.userGmc;
        String name = model.userName;
        String lastName = model.userLastName;
        String phone = model.userPhone;



        etName.setText(name+" "+lastName);
        etGmc.setText(GmcNum);
        etEmail.setText(email);
        etPhone.setText(phone);
    }





    public void enableFields(){
//        btnEdit.setVisibility(View.GONE);
//        btnSave.setVisibility(View.VISIBLE);

        etName.setEnabled(true);
        etName.setClickable(true);

        etGmc.setEnabled(true);
        etGmc.setClickable(true);

        etEmail.setEnabled(true);
        etEmail.setClickable(true);

        etPhone.setEnabled(true);
        etPhone.setClickable(true);
    }

    public void disableFields(){

//        btnEdit.setVisibility(View.VISIBLE);
//        btnSave.setVisibility(View.GONE);

        etName.setEnabled(false);
        etName.setClickable(false);

        etGmc.setEnabled(false);
        etGmc.setClickable(false);

        etEmail.setEnabled(false);
        etEmail.setClickable(false);

        etPhone.setEnabled(false);
        etPhone.setClickable(false);
    }


}
