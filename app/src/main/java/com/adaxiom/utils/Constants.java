package com.adaxiom.utils;

public interface Constants {

    String BASE_URL_LIVE = "http://www.locumset.co.uk/locum_set/device/";


    //Method
    String API_SIGN_UP = "sign_up";
    String API_APPLY_JOB = "apply";
    String API_CANCEL_JOB = "cancel_job";
    String API_FORGOT_PASS = "forgot_password";
    String API_LOGIN = "login_user";
    String API_JOB_LIST = "get_all_jobs/{user_id}";
    String API_APPLIED_JOB = "get_applied_jobs/{user_id}";
    String API_UPDATE_JOB_SHIFT = "update_job_shift";
    String API_DEP_LIST = "get_department_list";
    String API_HOSPITAL_LIST = "get_hospital_list";



    String PREF_TOTAL_AMOUNT = "pref_total_amount";
    String PREF_BREAK_TIME = "pref_break_time";
    String PREF_SIGNATURE = "pref_sign";
    String PREF_COMPLETE_PROFILE = "pref_comp_profile";
    String PREF_DEP_LIST = "pref_dep_list";
    String PREF_HOS_LIST = "pref_hos_list";
}
