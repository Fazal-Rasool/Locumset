package com.adaxiom.locumset;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adaxiom.database.DatabaseHelper;
import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.models.ModelRegister;
import com.adaxiom.network.ApiCalls;
import com.adaxiom.utils.SharedPrefrence;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {


    EditText email, password;
    Button login;
    String email_str, password_str;
    String token = "";

    DatabaseHelper dbHelper;

    private Subscription getSubscription;

    private View avLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getFirebaseToken();
        createObject();
        setViews();


    }

    private void createObject() {

        dbHelper = DatabaseHelper.getInstance();
    }


    public void getFirebaseToken() {
        FirebaseMessaging.getInstance().subscribeToTopic("com.adaxiom.locumset");
        String tkn = FirebaseInstanceId.getInstance().getToken();
        SharedPrefrence.setFcmToken(this, tkn);

    }


    public void setViews() {

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.password_login);
        login = (Button) findViewById(R.id.button_login);
        avLoading = (View) findViewById(R.id.avLoadingView);

        findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                API_Login();
            }
        });


        findViewById(R.id.tvForgotPassword).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                API_ForgotPassword();
            }
        });
    }


    private void API_ForgotPassword() {

        if (email.getText().toString().equalsIgnoreCase("") || email.getText() == null) {
            email.setError("Missing field");
        } else {


            if (getSubscription != null) {
                return;
            }

            avLoading.setVisibility(View.VISIBLE);

            getSubscription = DownloaderManager.getGeneralDownloader().ForgotPassword(email_str)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ModelLogin>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(final Throwable e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    avLoading.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onNext(final ModelLogin modelRegister) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    avLoading.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


//            final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "", " Please wait");
//            progressDialog.setCancelable(false);

//            ApiCalls callInterface = ApiClass.getClient().create(ApiCalls.class);
//            Call<ModelLogin> call = callInterface.ForgotPassword(email_str);
//
//            call.enqueue(new Callback<ModelLogin>() {
//                @Override
//                public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//                    ModelLogin modelLogin = response.body();
//                    if (response.code() == 200) {
//                        Toast.makeText(Login.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ModelLogin> call, Throwable t) {
//                    Toast.makeText(Login.this, "Response error!!!", Toast.LENGTH_SHORT).show();
//                }
//            });
        }


    }


    private void API_Login() {

        token = SharedPrefrence.getFcmToken(Login.this);

        if (email.getText().toString().equalsIgnoreCase("") || email.getText() == null) {
            email.setError("Missing field");
        } else if (password.getText().toString().equalsIgnoreCase("") || password.getText() == null) {
            password.setError("Missing field");

        } else {
            email_str = email.getText().toString();
            password_str = password.getText().toString();


//            if (getSubscription != null) {
//                return;
//            }

            avLoading.setVisibility(View.VISIBLE);

//            final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "", " Please wait");
//            progressDialog.setCancelable(false);

            getSubscription = DownloaderManager.getGeneralDownloader().LoginData(email_str, password_str, token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ModelLogin>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(final Throwable e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    avLoading.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onNext(final ModelLogin modelLogin) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    avLoading.setVisibility(View.GONE);
                                    if (!modelLogin.Error) {
                                        SharedPrefrence.setUserId(Login.this, modelLogin.user_id);
                                        dbHelper.saveUser(modelLogin);

                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                        SharedPrefrenceStorage.INSTANCE.setIsLogin(Login.this, "1");
                                        SharedPrefrence.setIsLogin(Login.this, "1");
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        Login.this.finish();
                                    } else {
                                        String message = modelLogin.Message;
                                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });


//            final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "", " Please wait");
//            progressDialog.setCancelable(false);
//
//            ApiCalls callInterface = ApiClass.getClient().create(ApiCalls.class);
//            Call<ModelLogin> call = callInterface.LoginData(email_str, password_str, token);
//
//            call.enqueue(new Callback<ModelLogin>() {
//                @Override
//                public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//                    ModelLogin modelLogin = response.body();
//                    if (!modelLogin.Error) {
//
//                        SharedPrefrence.setUserId(Login.this, modelLogin.user_id);
//                        dbHelper.saveUser(modelLogin);
//
//                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
////                        SharedPrefrenceStorage.INSTANCE.setIsLogin(Login.this, "1");
//                        SharedPrefrence.setIsLogin(Login.this, "1");
//                        Intent intent = new Intent(Login.this, MainActivity.class);
//                        startActivity(intent);
//                        Login.this.finish();
//                    } else {
//                        String message = modelLogin.Message;
//                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ModelLogin> call, Throwable t) {
//                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
//
//                }
//            });
        }


    }


    public void gotoSignup(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);

    }


}

