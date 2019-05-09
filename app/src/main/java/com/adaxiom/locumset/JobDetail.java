package com.adaxiom.locumset;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.network.ApiCalls;
import com.adaxiom.utils.SharedPrefrence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;


public class JobDetail extends AppCompatActivity {

    TextView tvTitle, tvPrice, tvDep, tvAdd, tvNote,
            tvDateBanner, tvEmail, tvPhone, tvPayGrade, tvGrade;
    EditText tvDate, tvTime;
    String strTitle, strPrice, strDep, strAdd, strFromDate,
            strStartTime, strJobId, strNote, strEndTime, strFlag,
            strToDate, strPayGrade, strGrade, strEmail, strPhone;
    int jobId = 0, intStatus = 0;
    Toolbar toolbar;
    CheckBox cbCheck;
    Button btnApply, btnCancel;
    View viewPayScale;

    private Subscription getSubscription;

    private static final int PERMISSION_REQUEST_CODE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar_detail_new);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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


        setViews();
        setListeners();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setViews() {

        tvTitle = (TextView) findViewById(R.id.tvDetailTitle);
        tvPrice = (TextView) findViewById(R.id.tvDetailPrice);
        tvDep = (TextView) findViewById(R.id.tvDetailDep);
        tvAdd = (TextView) findViewById(R.id.tvDetailAdd);
        tvDate = (EditText) findViewById(R.id.tvDetailDate);
        tvTime = (EditText) findViewById(R.id.tvDetailTime);
        tvNote = (TextView) findViewById(R.id.tvDetailNotes);
        btnApply = (Button) findViewById(R.id.btnDetailApply);
        btnCancel = (Button) findViewById(R.id.btnDetailCancel);
        tvDateBanner = (TextView) findViewById(R.id.tvDateBanner);
        tvEmail = (TextView) findViewById(R.id.tvDetailEmail);
        tvPhone = (TextView) findViewById(R.id.tvDetailPhone);
        tvPayGrade = (TextView) findViewById(R.id.tvPayGrade);
        tvGrade = (TextView) findViewById(R.id.tvDetailGrade);
        viewPayScale = (View) findViewById(R.id.viewPayScale);


        cbCheck = (CheckBox) findViewById(R.id.cbAgree);

        tvTitle.setText(strTitle);
        tvPrice.setText("£" + strPrice + " Per Hour");
        tvDep.setText(strDep);
        tvAdd.setText(strAdd);
        String newFromDate = parseDateInFormat(strFromDate);
        String newToDate = parseDateInFormat(strToDate);
        tvDate.setText(newFromDate + " to " + newToDate);
        tvTime.setText(strStartTime + " - " + strEndTime);
        tvPayGrade.setText("£" + strPayGrade);
        tvGrade.setText(strGrade);
        tvNote.setText(strNote);
        tvEmail.setText(strEmail);
        tvPhone.setText(strPhone);
        parseDate(strFromDate);


        if (strFlag.equalsIgnoreCase("1")) {
            btnApply.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
        } else {
            btnApply.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
        }


        if (strPayGrade.equalsIgnoreCase("")) {
            viewPayScale.setVisibility(View.GONE);
        } else {
            viewPayScale.setVisibility(View.VISIBLE);
        }

    }


    public void setListeners() {

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_ApplyJob();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intStatus == 0) {
                    Toast.makeText(JobDetail.this, "You can cancel this job after approval", Toast.LENGTH_LONG).show();
                } else if (intStatus == 3) {
                    Toast.makeText(JobDetail.this, "You already canceled this job", Toast.LENGTH_LONG).show();
                } else {
                    API_CancelJob();
                }

            }
        });

        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    btnApply.setBackgroundResource(R.drawable.button_enable);
                    btnApply.setClickable(true);

                    btnCancel.setBackgroundResource(R.drawable.button_enable);
                    btnCancel.setClickable(true);
                } else {
                    btnApply.setBackgroundResource(R.drawable.customborder_button_disable);
                    btnApply.setClickable(false);

                    btnCancel.setBackgroundResource(R.drawable.customborder_button_disable);
                    btnCancel.setClickable(false);
                }
            }
        });

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textEmail = tvEmail.getText().toString();

                Uri uri = Uri.parse("mailto:" + textEmail)
                        .buildUpon()
                        .appendQueryParameter("subject", "Locumset")
//                        .appendQueryParameter("body", "Hello")
                        .build();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {

                    String textPhone = tvPhone.getText().toString();


                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + textPhone));
                    startActivity(intent);

                } else {
                    requestPermission();
                }

            }
        });

    }


    public String parseDateInFormat(String strDate) {
        Date date = null;
        String newDate = null;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = spf.parse(strDate);
            spf = new SimpleDateFormat("dd-MM-yyyy");
            newDate = spf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }


    public void parseDate(String strDate) {
        Date date = null;
        String dayNum = "", mon = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            dayNum = (String) DateFormat.format("dd", date); // 20
            mon = (String) DateFormat.format("MMM", date); // Jun
        }

        tvDateBanner.setText(mon + "\n" + dayNum);
    }


    public void API_ApplyJob() {

        int userId = SharedPrefrence.getUserId(this);

        if (getSubscription != null) {
            return;
        }

        getSubscription = DownloaderManager.getGeneralDownloader().ApplyJob(jobId, userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ModelJobApply>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(JobDetail.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final ModelJobApply modelJobApply) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(JobDetail.this, modelJobApply.message, Toast.LENGTH_SHORT).show();
                                JobDetail.this.finish();
                            }
                        });
                    }
                });


//        final ProgressDialog progressDialog = ProgressDialog.show(this, "", " Please wait");
//        progressDialog.setCancelable(false);
//
//        ApiCalls callInterface = ApiClass.getClient().create(ApiCalls.class);
//        Call<ModelJobApply> call = callInterface.ApplyJob(jobId, userId);
//
//        call.enqueue(new Callback<ModelJobApply>() {
//            @Override
//            public void onResponse(Call<ModelJobApply> call, Response<ModelJobApply> response) {
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
//                if (response.code() == 200) {
//                    ModelJobApply modelJobApply = response.body();
//                    Toast.makeText(JobDetail.this, modelJobApply.message, Toast.LENGTH_SHORT).show();
//                    JobDetail.this.finish();
//                } else {
//                    Toast.makeText(JobDetail.this, "Bad request!!!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ModelJobApply> call, Throwable t) {
//                Toast.makeText(JobDetail.this, "Error during apply for a job", Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }


    public void API_CancelJob() {

        int userId = SharedPrefrence.getUserId(this);


        if (getSubscription != null) {
            return;
        }

        getSubscription = DownloaderManager.getGeneralDownloader().CancelJob(jobId, userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ModelJobApply>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(JobDetail.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final ModelJobApply modelJobApply) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(JobDetail.this, modelJobApply.message, Toast.LENGTH_SHORT).show();
                                JobDetail.this.finish();
                            }
                        });
                    }
                });




//        final ProgressDialog progressDialog = ProgressDialog.show(this, "", " Please wait");
//        progressDialog.setCancelable(false);
//
//        ApiCalls callInterface = ApiClass.getClient().create(ApiCalls.class);
//        Call<ModelJobApply> call = callInterface.CancelJob(jobId, userId);
//
//        call.enqueue(new Callback<ModelJobApply>() {
//            @Override
//            public void onResponse(Call<ModelJobApply> call, Response<ModelJobApply> response) {
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
//                if (response.code() == 200) {
//                    ModelJobApply modelJobApply = response.body();
//                    Toast.makeText(JobDetail.this, modelJobApply.message, Toast.LENGTH_SHORT).show();
//                    JobDetail.this.finish();
//                } else {
//                    Toast.makeText(JobDetail.this, "Bad request!!!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ModelJobApply> call, Throwable t) {
//                Toast.makeText(JobDetail.this, "Error during apply for a job", Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                PERMISSION_REQUEST_CODE);

        //
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
