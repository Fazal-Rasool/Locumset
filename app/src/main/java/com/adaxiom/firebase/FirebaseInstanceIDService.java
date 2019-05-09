package com.adaxiom.firebase;

import com.adaxiom.utils.SharedPrefrence;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        SharedPrefrence.setFcmToken(this, token);
//        Log.e("Received","Token ["+token+"]");
//        String IMEI = Utility.getIMEI(getApplicationContext());
//        uploadToken(IMEI, token);
    }

//    public void uploadToken(String IMEI, String Token) {
//
//        ApiCalls callInterface = ApiClass.getClient("http://5.9.147.58/playstore_live/apis/device/").create(ApiCalls.class);
//        Call<Void> callUser = callInterface.postToken(Token, IMEI);
//        callUser.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//                if (response.code() == 200) {
//
//                    String message = response.message().toString();
//
//                } else {
////                    Utility.showShortToast(LauncherHomeActivity.this, response.code() + "");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//                Log.e("Message:", t.getMessage());
//            }
//        });
//    }



}
