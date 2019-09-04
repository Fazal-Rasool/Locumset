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

import okhttp3.Call;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

import static com.adaxiom.utils.Constants.API_APPLIED_JOB;
import static com.adaxiom.utils.Constants.API_APPLY_JOB;
import static com.adaxiom.utils.Constants.API_CANCEL_JOB;
import static com.adaxiom.utils.Constants.API_DEP_LIST;
import static com.adaxiom.utils.Constants.API_FORGOT_PASS;
import static com.adaxiom.utils.Constants.API_HOSPITAL_LIST;
import static com.adaxiom.utils.Constants.API_JOB_LIST;
import static com.adaxiom.utils.Constants.API_LOGIN;
import static com.adaxiom.utils.Constants.API_SIGN_UP;
import static com.adaxiom.utils.Constants.API_UPDATE_JOB_SHIFT;

public interface ApiCalls {


//    @Multipart
//    @POST("signup")
//    Call<ModelSignup> RegisterData(@Part("name") String name,
//                                   @Part("city") String city,
//                                   @Part("email") String email,
//                                   @Part("password") String password,
//                                   @Part MultipartBody.Part image_1
//
//    );
//
//

    @FormUrlEncoded
    @POST(API_SIGN_UP)
    Observable<ModelRegister> Register(
            @Field("name") String name,
            @Field("last_name") String lastname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gmc_number") String gmcNum
    );


    @FormUrlEncoded
    @POST(API_APPLY_JOB)
    Observable<ModelJobApply> ApplyJob(
            @Field("job_id") int job_id,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST(API_CANCEL_JOB)
    Observable<ModelJobApply> CancelJob(
            @Field("job_id") int job_id,
            @Field("user_id") int user_id
    );


    @FormUrlEncoded
    @POST(API_FORGOT_PASS)
    Observable<ModelLogin> ForgotPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST(API_LOGIN)
    Observable<ModelLogin> LoginData(
            @Field("email") String email,
            @Field("password") String password,
            @Field("fcm_token") String token
    );
//
//
//    @GET("get_jobs")
//    Call<List<ModelJobList>> GetJobsList();

    @GET(API_JOB_LIST)
    Observable<List<ModelJobList>> GetJobList(@Path("user_id") int user_id);

//    @GET("get_approved_jobs/{user_id}")
//    Call<List<ModelJobList>> GetMyJobs(@Path("user_id") int user_id);

    @GET(API_APPLIED_JOB)
    Observable<List<ModelMyShifts>> GetAppliedJobs(@Path("user_id") int user_id);


    @FormUrlEncoded
//    @Multipart
    @POST(API_UPDATE_JOB_SHIFT)
    Observable<ModelUpdateJob> UpdateJobShift(
            @Field("job_id") int jobId,
            @Field("extra_hours") String extraHours,
            @Field("field_1") String field1,
            @Field("field_2") String field2,
            @Field("image") String image
    );


    @GET(API_DEP_LIST)
    Observable<List<ModelDepList>> GetDepList();


    @GET(API_HOSPITAL_LIST)
    Observable<List<ModelHospitalList>> GetHospitalList();


    @Multipart
    @POST("update_user_profile")
    Observable<ModelJobApply> UpdateMyProfile(@Part("user_id") int user_id,
                                              @Part("department_id") int depid,
                                              @Part("hospital_id") int hosid,
                                              @Part("hospital_member") String member,
                                              @Part MultipartBody image_3,
                                              @Part("field_1") String field1,
                                              @Part("field_2") String field2
    );


//
//    @GET("user_info/{user_id}")
//    Call<List<JsonResponseGetUserDetails>> getJSONProfileDetail(@Path("user_id") int user_id);
//
//    @GET("chef_info")
//    Call<List<JsonResponseGetChefs>> getJSONChefs();
//
//    @GET("user_recipes/{user_id}")
//    Call<List<JsonResponseGetChefRecipes>> getJSONChefRecipes(@Path("user_id") int user_id);
//
//    @GET("queries")
//    Call<List<JsonResponseGetQueries>> getJSONQueries();
//
//
//    @FormUrlEncoded
//    @POST("add_query")
//    Call<Modelquery> addQuery(
//            @Field("user_id") int user_id,
//            @Field("query") String query
//    );
//
//
//    @GET("query_detail/{query_id}")
//    Call<List<JsonResponseGetQueryDetails>> getJSONQueryDetails(@Path("query_id") int query_id);
//
//    @FormUrlEncoded
//    @POST("add_rating")
//    Call<ModelAddRating> addRating(
//            @Field("chef_id") int chef_id,
//            @Field("rated") String rated
//    );
//
//    @FormUrlEncoded
//    @POST("rating_comments")
//    Call<ModelCommentOnQuery> addComment(
//            @Field("user_id") int user_id,
//            @Field("query_id") int query_id,
//            @Field("comments") String comments
//    );
//
//    @FormUrlEncoded
//    @POST("add_rating")
//    Call<ModelQueryLike> addLike(
//            @Field("user_id") int user_id,
//            @Field("query_id") int query_id,
//            @Field("likes") int likes
//
//    );
}
