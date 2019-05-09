package com.adaxiom.network;



import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelJobList;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.models.ModelMyShifts;
import com.adaxiom.models.ModelRegister;

import java.util.List;

import rx.Observable;


public interface BackendConnector {

    void setupConnector(String apiEndPointLive, String apiEndPointStaging);

    GeneralApis getGeneralDownloader();

    interface GeneralApis {
//        Observable<List<ModelJobList>> getAllJobs(int userId);

        Observable<ModelRegister> Register(String name, String lastname, String email, String pswrd, String gmcNum);

        Observable<ModelJobApply> ApplyJob(int jobId, int userId);

        Observable<ModelJobApply> CancelJob(int jobId, int userId);

        Observable<ModelLogin> ForgotPassword(String email);

        Observable<ModelLogin> LoginData(String email, String password, String token);

        Observable<List<ModelJobList>> GetJobList(int userId);

        Observable<List<ModelMyShifts>> GetAppliedJobs(int userId);

//
//        Observable<List<RM_MatchActive>> matchActive();
//
//        Observable<List<RM_BlockList>> BlockList();
//
//        Observable<List<RM_CityList>> CityList();
//
//        Observable<RM_MatchPrediction> PostMatchPrediction(
//                String userId,
//                String matchId,
//                String blockId,
//                String innings,
//                String matchOver,
//                String ball_1,
//                String ball_2,
//                String ball_3,
//                String ball_4,
//                String ball_5,
//                String ball_6
//        );

    }
}