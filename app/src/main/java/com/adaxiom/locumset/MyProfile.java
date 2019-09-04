package com.adaxiom.locumset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.adaxiom.database.DatabaseHelper;
import com.adaxiom.models.ModelDepList;
import com.adaxiom.models.ModelHospitalList;
import com.adaxiom.models.ModelUser;
import com.adaxiom.utils.RuntimePermissions;
import com.adaxiom.utils.SharedPrefrence;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.adaxiom.utils.Constants.PREF_DEP_LIST;
import static com.adaxiom.utils.Constants.PREF_HOS_LIST;


public class MyProfile extends AppCompatActivity {


    private TextView tv_phone, tvName, tvGmcNum;
    private String card;

    private ImageView ivProfile;
    DatabaseHelper dbHelper;

    private MainActivity mainActivity;
    private Toolbar toolbar;

    android.support.v4.app.FragmentManager mFragmentManager;
    android.support.v4.app.FragmentTransaction mFragmentTransaction;

    Spinner spDep, spHos;

    static String imagePath="";


    private static final int CAMERA_REQUEST = 1888;
    public static final int GALLERY_REQUEST = 1999;



    public static void startActivity(Context context){
        Intent intent = new Intent(context, MyProfile.class);
        context.startActivity(intent);    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        createObjects();
        setViews();
        setValues();

    }


    //    @Override
//    public View onCreate(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_my_profile_new, container, false);
//
//
//        createObjects();
//        setViews();
//        setValues();
//
//        return view;
//    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getActivity(), "Call", Toast.LENGTH_SHORT).show();
//    }

    public void setViews() {

        tvName = (TextView) findViewById(R.id.tv_myProfileName);
        tvGmcNum = (TextView) findViewById(R.id.tv_myProfileGmcNum);
        spDep = findViewById(R.id.spDep_MyProfile);
        spHos = findViewById(R.id.spHospital_MyProfile);
        ivProfile = (ImageView) findViewById(R.id.iv_myProfile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RuntimePermissions.checkPermission(MyProfile.this)) {
                    showBottomDialog();
                } else {
                    RuntimePermissions.requestPermission(MyProfile.this);
                }
            }
        });

    }

    public void createObjects() {

        dbHelper = DatabaseHelper.getInstance();
    }


    private void setValues() {


        int userId = SharedPrefrence.getUserId(MyProfile.this);
        ModelUser model = dbHelper.getUserList(userId);
        String name = model.userName;
        String lastName = model.userLastName;
        String gmcNum = model.userGmc;
        String imageUrl =  model.userImage;

        tvGmcNum.setText("GMC : " + gmcNum);
        tvName.setText(name+" "+lastName);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.doctor)
                .error(R.drawable.doctor)
                .into(ivProfile);


        Gson gson = new Gson();
        String json = Prefs.getString(PREF_HOS_LIST, "");
        Type type = new TypeToken<List<ModelHospitalList>>(){}.getType();
        List<ModelHospitalList> listModel = gson.fromJson(json, type);

        ArrayList<String> list = new ArrayList<>();
        for (ModelHospitalList hModel : listModel) {
            list.add(hModel.hospital_name);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spHos.setAdapter(spinnerArrayAdapter);

        String jsonDep = Prefs.getString(PREF_DEP_LIST, "");
        Type typeDep = new TypeToken<List<ModelDepList>>(){}.getType();
        List<ModelDepList> listModelDep = gson.fromJson(jsonDep, typeDep);

        ArrayList<String> listDepName = new ArrayList<>();
        for (ModelDepList dModel : listModelDep) {
            list.add(dModel.department_name);
        }

        ArrayAdapter<String> spinnerArrayAdapterDep = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listDepName);
        spinnerArrayAdapterDep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spDep.setAdapter(spinnerArrayAdapterDep);




    }


//    public void loadFragment(Fragment fragment) {
//        mFragmentManager = getFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.conMyProfile, fragment).commit();
//    }


    public void showBottomDialog() {

        View mView = getLayoutInflater().inflate(R.layout.bottom_dialog_layout, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(MyProfile.this);
        dialog.setContentView(mView);


        View btnCamera = (View) mView.findViewById(R.id.viewCam);
        View btnGallery = (View) mView.findViewById(R.id.viewGallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                dialog.dismiss();

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GALLERY_REQUEST);
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public static String getImagePath(){
        return imagePath;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(MyProfile.this, photo);
            imagePath = getImageRealPath(MyProfile.this, tempUri);
            ivProfile.setImageBitmap(photo);

        } else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(MyProfile.this.getContentResolver(), uri);
                imagePath = getImageRealPath(MyProfile.this, uri);
                ivProfile.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    public String getImageRealPath(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }





}
