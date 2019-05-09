package com.adaxiom.locumset;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.models.ModelRegister;
import com.adaxiom.network.ApiCalls;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class Register extends AppCompatActivity {

    EditText etName, etLastName, etEmail, etPassword, etGmcNum;
    Button btnLogin;

    private Subscription getSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createObjects();
        setViews();
    }

    public void createObjects() {

    }

    public void setViews() {

        etName = (EditText) findViewById(R.id.etRegName);
        etLastName = (EditText) findViewById(R.id.etRegLastName);
        etEmail = (EditText) findViewById(R.id.etRegEmail);
        etPassword = (EditText) findViewById(R.id.etRegPassword);
        etGmcNum = (EditText) findViewById(R.id.etRegGmcNum);
        btnLogin = (Button) findViewById(R.id.btnRegLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_Register();
            }
        });


    }

    public void API_Register() {

        String name, lastName, email, password, gmcNum;
        name = etName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        gmcNum = etGmcNum.getText().toString().trim();

//        final ProgressDialog progressDialog = ProgressDialog.show(Register.this, "", " Please wait");
//        progressDialog.setCancelable(false);

        if (getSubscription != null) {
            return;
        }

        getSubscription = DownloaderManager.getGeneralDownloader().Register(name, lastName, email, password, gmcNum)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ModelRegister>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final ModelRegister modelRegister) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, modelRegister.message, Toast.LENGTH_SHORT).show();
                                Register.this.finish();
                            }
                        });
                    }
                });


//        ApiCalls callInterface = ApiClass.getClient().create(ApiCalls.class);
//        Call<ModelRegister> call = callInterface.Register(name, lastName, email, password, gmcNum);
//
//        call.enqueue(new Callback<ModelRegister>() {
//            @Override
//            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
//                if(response.code() == 200) {
//                    ModelRegister modelRegister = response.body();
//                    Toast.makeText(Register.this, modelRegister.message, Toast.LENGTH_SHORT).show();
//                    Register.this.finish();
//                }else{
//                    Toast.makeText(Register.this, "Bad Request Error!!!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ModelRegister> call, Throwable t) {
//                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }
}
