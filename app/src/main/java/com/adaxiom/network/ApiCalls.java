package com.adaxiom.network;

import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelJobList;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.models.ModelMyShifts;
import com.adaxiom.models.ModelRegister;

import java.util.List;

import okhttp3.Call;
import okhttp3.MultipartBody;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import rx.Observable;

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
    @POST("/sign_up")
    Observable<ModelRegister> Register(
            @Field("name") String name,
            @Field("last_name") String lastname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gmc_number") String gmcNum
    );


    @FormUrlEncoded
    @POST("/apply")
    Observable<ModelJobApply> ApplyJob(
            @Field("job_id") int job_id,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("/cancel_job")
    Observable<ModelJobApply> CancelJob(
            @Field("job_id") int job_id,
            @Field("user_id") int user_id
    );


    @FormUrlEncoded
    @POST("/forgot_password")
    Observable<ModelLogin> ForgotPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST("/login_user")
    Observable<ModelLogin> LoginData(
            @Field("email") String email,
            @Field("password") String password,
            @Field("fcm_token") String token
    );
//
//
//    @GET("get_jobs")
//    Call<List<ModelJobList>> GetJobsList();

    @GET("/get_all_jobs/{user_id}")
    Observable<List<ModelJobList>> GetJobList(@Path("user_id") int user_id);

//    @GET("get_approved_jobs/{user_id}")
//    Call<List<ModelJobList>> GetMyJobs(@Path("user_id") int user_id);

    @GET("/get_applied_jobs/{user_id}")
    Observable<List<ModelMyShifts>> GetAppliedJobs(@Path("user_id") int user_id);


//    @Multipart
//    @POST("update_user_profile")
//    Observable<ModelRegister> UpdateMyProfile(@Part("user_id") int user_id,
//                                   @Part("phone_no") String phone,
//                                   @Part MultipartBody image_3
//
//    );























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
