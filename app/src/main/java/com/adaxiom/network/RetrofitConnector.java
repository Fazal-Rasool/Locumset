package com.adaxiom.network;


import com.adaxiom.locumset.BuildConfig;
import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelJobList;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.models.ModelMyShifts;
import com.adaxiom.models.ModelRegister;
import com.adaxiom.models.ModelUpdateJob;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RetrofitConnector implements BackendConnector, BackendConnector.GeneralApis {

//    private RestAdapter restAdapter;

    private String apiEndPoint_Staging, apiEndPoint_Live;
    private ApiCalls calls;
    private Retrofit retrofit;

//    RequestInterceptor requestInterceptor = new RequestInterceptor() {
//        @Override
//        public void intercept(RequestFacade request) {
//            request.addHeader("Content-Type", "multipart/form-data");
//        }
//    };


    @Override
    public void setupConnector(String live, String staging) {
        this.apiEndPoint_Live = live;
        this.apiEndPoint_Staging = staging;

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();

        if (BuildConfig.DEBUG)

//            restAdapter = new RestAdapter.Builder().setEndpoint(apiEndPoint_Live).
//                    setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.FULL)
//                    .build();

//            restAdapter = new RestAdapter
//                    .Builder()
//                    .setEndpoint(apiEndPoint_Live)
////                    .setLogLevel(RestAdapter.LogLevel.FULL)
//                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(apiEndPoint_Live)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        else
            retrofit = new Retrofit.Builder()
                    .baseUrl(apiEndPoint_Live)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

//            restAdapter = new RestAdapter
//                    .Builder()
//                    .setEndpoint(apiEndPoint_Staging)
////                    .setLogLevel(RestAdapter.LogLevel.NONE)
//                    .build();

//            restAdapter = new RestAdapter.Builder().setEndpoint(apiEndPoint_Staging)
//                    .setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.NONE)
//                    .build();
//        calls = restAdapter.create(ApiCalls.class);

         calls = retrofit.create(ApiCalls.class);

    }

    @Override
    public GeneralApis getGeneralDownloader() {
        return this;
    }


//    @Override
//    public Observable<List<ModelJobList>> getAllJobs(int userId) {
//        return calls.GetJobList(userId);
//    }

//    @Override
//    public Observable<RM_SignUp> signUp(SignUpBody signUpBody) {
//        return calls.Sign_Up(signUpBody);
//    }
//
//    @Override
//    public Observable<RM_Login> Login(LoginBody loginBody) {
////        return calls.Login(loginBody);
//        return null;
//    }


    @Override
    public Observable<ModelRegister> Register(String name, String lastname, String email, String pswrd, String gmcNum) {
        return calls.Register(name, lastname, email, pswrd, gmcNum);
    }


    @Override
    public Observable<ModelJobApply> ApplyJob(int jobId, int userId) {
        return calls.ApplyJob(jobId, userId);
    }

    @Override
    public Observable<ModelJobApply> CancelJob(int jobId, int userId) {
        return calls.CancelJob(jobId , userId);
    }

    @Override
    public Observable<ModelLogin> ForgotPassword(String email) {
        return calls.ForgotPassword(email);
    }


    @Override
    public Observable<ModelLogin> LoginData(String email, String password, String token) {
        return calls.LoginData(email,password,token);
    }

    @Override
    public Observable<List<ModelJobList>> GetJobList(int userId) {
        return calls.GetJobList(userId);
    }

    @Override
    public Observable<List<ModelMyShifts>> GetAppliedJobs(int userId) {
        return calls.GetAppliedJobs(userId);
    }

    @Override
    public Observable<ModelUpdateJob> UpdateShift(int jobid, String extraHours, String field1, String field2, MultipartBody img) {
        return calls.UpdateJobShift(jobid, extraHours, field1, field2, img);
    }

//    @Override
//    public Observable<RM_MatchPrediction> PostMatchPrediction(String userId,
//                                                              String matchId,
//                                                              String blockId,
//                                                              String innings,
//                                                              String matchOver,
//                                                              String ball_1,
//                                                              String ball_2,
//                                                              String ball_3,
//                                                              String ball_4,
//                                                              String ball_5,
//                                                              String ball_6) {
//
//        return calls.PostMatchPrediction(userId, matchId, blockId, innings, matchOver, ball_1, ball_2, ball_3, ball_4, ball_5, ball_6);
//    }

    //    @Override
//    public Observable<RM_SignUp> signUp(String name, String uName, String email, String pswrd, String fcmToken, String city) {
//        return calls.Sign_Up(name, uName, email, pswrd, fcmToken, city);
//    }
}
