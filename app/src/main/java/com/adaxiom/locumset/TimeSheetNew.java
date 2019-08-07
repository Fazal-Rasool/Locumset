package com.adaxiom.locumset;

import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adaxiom.database.DatabaseHelper;
import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.models.ModelUpdateJob;
import com.adaxiom.models.ModelUser;
import com.adaxiom.utils.RuntimePermissions;
import com.adaxiom.utils.SharedPrefrence;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class TimeSheetNew extends AppCompatActivity implements View.OnClickListener {


    Spinner spGrade;
    TextView btnClear, btnSave, tvPriceHour, tvStartTime, tvEndTime, tvTotalAmount, tvBreakTime, tvGMC;
    SignaturePad signaturePad;
    Button btnSubmit;

    EditText etComments;

    String strTitle, strPrice, strDep, strAdd, strFromDate,
            strStartTime, strJobId, strNote, strEndTime, strFlag,
            strToDate, strPayGrade, strGrade, strEmail, strPhone;
    String postImagePath="";

    int jobId = 0, intStatus = 0, total_amount=0, pri=0;

    DatabaseHelper dbHelper;

    private View avLoading;
    String base64Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet_new);

        setTitle("Time Sheet");

        getData();
        createObjects();
        setViews();
        setListeners();

    }

    private void createObjects() {

        dbHelper = DatabaseHelper.getInstance();

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



    private void setViews() {

        spGrade = findViewById(R.id.spGrade_TimeSheetNew);

        btnClear = findViewById(R.id.tvClearSignTimeSheetNew);
        btnSave = findViewById(R.id.tvSaveSignTimeSheetNew);
        tvPriceHour = findViewById(R.id.tvPricePerHourTimeSheetNew);
        tvStartTime = findViewById(R.id.tvStartTimeTimeSheetNew);
        tvEndTime = findViewById(R.id.tvEndTimeTimeSheetNew);
        tvTotalAmount = findViewById(R.id.tvTotalAmountTimeSheetNew);
        tvBreakTime = findViewById(R.id.tvBreakTimeSheetNew);
        tvGMC = findViewById(R.id.tvGMCTimeSheetNew);
        btnSubmit = findViewById(R.id.btnSubmitTimeSheetNew);

        etComments = findViewById(R.id.etCommetsTimeSheetNew);

        avLoading = findViewById(R.id.avLoadingView);

        signaturePad = findViewById(R.id.signaturePadTimeSheetNew);


        int userId = SharedPrefrence.getUserId(this);
        ModelUser model = dbHelper.getUserList(userId);
        String gmcNum = model.userGmc;


        tvPriceHour.setText("£"+strPrice+" Per Hour");
        tvStartTime.setText(strStartTime);
        tvEndTime.setText(strEndTime);
        tvGMC.setText(gmcNum);

        calculateTotalAmount();

    }


    public void setListeners(){

        btnSave.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        tvBreakTime.setOnClickListener(this);
        tvPriceHour.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() { }
            @Override
            public void onSigned() { }
            @Override
            public void onClear() { }
        });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvPricePerHourTimeSheetNew:

                break;
            case R.id.tvBreakTimeSheetNew:

                break;
            case R.id.tvStartTimeTimeSheetNew:

                showTimePickerDialoge(1);

                break;
            case R.id.tvEndTimeTimeSheetNew:

                showTimePickerDialoge(2);

                break;
            case R.id.tvSaveSignTimeSheetNew:

                if (RuntimePermissions.checkPermission(TimeSheetNew.this)) {
                    Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                    Uri tempUri = getImageUri(TimeSheetNew.this, signatureBitmap);
                    postImagePath = getImageRealPath(TimeSheetNew.this, tempUri);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Toast.makeText(TimeSheetNew.this, "Signature Saved", Toast.LENGTH_LONG).show();
                } else {
                    RuntimePermissions.requestPermission(TimeSheetNew.this);
                }

                break;
            case R.id.tvClearSignTimeSheetNew:
                signaturePad.clear();
                break;
            case R.id.btnSubmitTimeSheetNew:
                APIUpdateJob();
                break;
        }
    }



    public void showTimePickerDialoge(final int flag){
        TimePickerDialog mTimePicker;
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(TimeSheetNew.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(flag == 1) {
                    tvStartTime.setText(selectedHour + ":" + selectedMinute);
                    calculateTotalAmount();
                }else if(flag == 2){
                    tvEndTime.setText(selectedHour + ":" + selectedMinute);
                    calculateTotalAmount();
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }




    public void calculateTotalAmount(){


        String timeOne, timeTwo;

        timeOne = tvStartTime.getText().toString().trim();
        timeTwo = tvEndTime.getText().toString().trim();

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

        tvTotalAmount.setText("£"+total_amount+"");


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



    public void APIUpdateJob() {

        int userId = SharedPrefrence.getUserId(this);
        String comm = etComments.getText().toString().trim();


        avLoading.setVisibility(View.VISIBLE);

        DownloaderManager.getGeneralDownloader().UpdateShift(jobId, "0", total_amount+"", comm, base64Image)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ModelUpdateJob>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(TimeSheetNew.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final ModelUpdateJob model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(TimeSheetNew.this, model.message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }






}
