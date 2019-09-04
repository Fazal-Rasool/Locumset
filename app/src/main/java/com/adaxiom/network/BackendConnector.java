package com.adaxiom.network;



import com.adaxiom.models.ModelDepList;
import com.adaxiom.models.ModelHospitalList;
import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelJobList;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.models.ModelMyShifts;
import com.adaxiom.models.ModelRegister;
import com.adaxiom.models.ModelUpdateJob;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
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

        Observable<ModelUpdateJob> UpdateShift(int jobid, String extraHours, String field1, String field2, String image);

        Observable<List<ModelDepList>> GetDepList();

        Observable<List<ModelHospitalList>> GetHospitalList();

        Observable<ModelJobApply> UpdateProfile(int userid, int hId, int dId, String mem, MultipartBody body, String field1, String field2);



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
