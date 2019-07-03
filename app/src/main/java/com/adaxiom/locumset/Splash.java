package com.adaxiom.locumset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.adaxiom.firebase.FirebaseInstanceIDService;
import com.adaxiom.utils.SharedPrefrence;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    String tkn="";

    String Token ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getFirebaseToken();


    }


    @Override
    protected void onResume() {
        super.onResume();
//        registerBroadCastReceiver();
    }

    public void setViews(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }


    public void getFirebaseToken() {
        FirebaseMessaging.getInstance().subscribeToTopic("com.adaxiom.locumset");
        tkn = FirebaseInstanceId.getInstance().getToken();

        if (tkn == null) {
            new FirebaseInstanceIDService();
        }

        SharedPrefrence.setFcmToken(this,tkn);

        setViews();

    }


    private void checkLogin() {

//        String value=SharedPrefrenceStorage.INSTANCE.getIsLogin(Splash.this);
        String value = SharedPrefrence.getIsLogin(this);

        if(!value.equalsIgnoreCase("0")) {
            Intent intent=new Intent(Splash.this,MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent=new Intent(Splash.this,Login.class);
            startActivity(intent);
        }
    }


//    public void registerBroadCastReceiver() {
//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.adaxiom.firebase.onMessageReceived");
//        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
//        registerReceiver(receiver, intentFilter);
//    }

//    private class MyBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Bundle extras = intent.getExtras();
//            String state = extras.getString("extra");
//            Toast.makeText(Splash.this,state, Toast.LENGTH_LONG).show();
////            if (Utility.isInternetConnected(LauncherHomeActivity.this)) {
////                if (state.equalsIgnoreCase("1")) {
////                    ApiDeviceImageBlockStatus();
////                } else if (state.equalsIgnoreCase("0")) {
////                    runInstallationSevices();
////                }
////
////            }
//
//
//        }
//    }

}
