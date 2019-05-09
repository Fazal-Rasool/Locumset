package com.adaxiom.subFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.adaxiom.database.DatabaseHelper;
import com.adaxiom.fragments.FragMyProfile;
import com.adaxiom.locumset.R;
import com.adaxiom.models.ModelRegister;
import com.adaxiom.models.ModelUser;
import com.adaxiom.network.ApiCalls;
import com.adaxiom.utils.SharedPrefrence;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class FragSettings extends Fragment {

    View view;

    DatabaseHelper dbHelper;
    EditText etName, etPhone, etPassword;
    String email, password, phone;
    FloatingActionButton btnEdit, btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        createObjects();
        setViews();
        setListeners();
        getDataFromDB();
        disableFields();

        return view;
    }


    private void setViews() {

        etName = (EditText) view.findViewById(R.id.etSettingsName);
        etPassword = (EditText) view.findViewById(R.id.etSettingsPassword);
        etPhone = (EditText) view.findViewById(R.id.etSettingsPhone);

        btnEdit = (FloatingActionButton)view.findViewById(R.id.btnEditInfo);
        btnSave = (FloatingActionButton)view.findViewById(R.id.btnSaveInfo);


    }

    private void createObjects() {

        dbHelper = DatabaseHelper.getInstance();

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
                API_UpdateMyProfile();
            }
        });

    }


    private void getDataFromDB() {
        int userId = SharedPrefrence.getUserId(getActivity());
        ModelUser model =dbHelper.getUserList(userId);
        email = model.userEmail;
        password = model.userPass;
        phone = model.userPhone;
//        String userLastName = model.userLastName;



        etName.setText(email);
        etPassword.setText(password);
        etPhone.setText(phone);
    }



    public void API_UpdateMyProfile() {

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", " Please wait");
        progressDialog.setCancelable(false);


        String phone, image;
        File file = null;
        RequestBody requestimage;
        MultipartBody.Part body = null;

        phone = etPhone.getText().toString().trim();
        image = FragMyProfile.getImagePath();
        int userId = SharedPrefrence.getUserId(getActivity());

        if (image != null && !image.equalsIgnoreCase("")) {
            file = new File(image);
        }
        if (file != null) {
            requestimage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("image_1", file.getName(), requestimage);
        }



//        ApiCalls callInterface = ApiClass.getClient().create(ApiCalls.class);
//        Call<ModelRegister> call = callInterface.UpdateMyProfile(userId, phone, body);
//
//        call.enqueue(new Callback<ModelRegister>() {
//            @Override
//            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
//                if(response.code() == 200) {
//                    ModelRegister modelRegister = response.body();
//                    Toast.makeText(getActivity(), modelRegister.message, Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getActivity(), "Bad Request Error!!!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ModelRegister> call, Throwable t) {
//                Toast.makeText(getActivity(), "Error while update profile", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }




    public void enableFields(){

        btnEdit.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);


        etName.setEnabled(true);
        etPhone.setEnabled(true);
        etPassword.setEnabled(true);

        etName.setClickable(true);
        etPhone.setClickable(true);
        etPassword.setClickable(true);

    }

    public void disableFields(){

        btnEdit.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);


        etName.setEnabled(false);
        etPhone.setEnabled(false);
        etPassword.setEnabled(false);

        etName.setClickable(false);
        etPhone.setClickable(false);
        etPassword.setClickable(false);

    }



}
