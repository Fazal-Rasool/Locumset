package com.adaxiom.downloaders;

import com.adaxiom.models.ModelDepList;
import com.adaxiom.models.ModelHospitalList;
import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelJobList;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.models.ModelMyShifts;
import com.adaxiom.models.ModelRegister;
import com.adaxiom.models.ModelUpdateJob;
import com.adaxiom.network.BackendConnector;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class GeneralDownloader extends BaseContentDownloader<BackendConnector.GeneralApis> {

    public GeneralDownloader(BackendConnector.GeneralApis beConnector) {
        super(beConnector);
    }



    public Observable<ModelRegister> Register(final String name,
                                                final String lastname,
                                                final String email,
                                                final String pswrd,
                                                final String gmcNum
    ) {

        return Observable.create(new Observable.OnSubscribe<ModelRegister>() {
            @Override
            public void call(final Subscriber<? super ModelRegister> subscriber) {
                beConnector.Register(name,lastname,email,pswrd,gmcNum)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ModelRegister>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(ModelRegister authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }




    public Observable<ModelJobApply> ApplyJob(final int jobId, final int userId) {

        return Observable.create(new Observable.OnSubscribe<ModelJobApply>() {
            @Override
            public void call(final Subscriber<? super ModelJobApply> subscriber) {
                beConnector.ApplyJob(jobId, userId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ModelJobApply>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(ModelJobApply authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }


    public Observable<ModelJobApply> CancelJob(final int jobId, final int userId) {

        return Observable.create(new Observable.OnSubscribe<ModelJobApply>() {
            @Override
            public void call(final Subscriber<? super ModelJobApply> subscriber) {
                beConnector.CancelJob(jobId, userId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ModelJobApply>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(ModelJobApply authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<ModelLogin> ForgotPassword(final String email) {

        return Observable.create(new Observable.OnSubscribe<ModelLogin>() {
            @Override
            public void call(final Subscriber<? super ModelLogin> subscriber) {
                beConnector.ForgotPassword(email)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ModelLogin>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(ModelLogin authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<ModelLogin> LoginData(final String email, final String password, final String token) {

        return Observable.create(new Observable.OnSubscribe<ModelLogin>() {
            @Override
            public void call(final Subscriber<? super ModelLogin> subscriber) {
                beConnector.LoginData(email, password, token)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ModelLogin>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(ModelLogin authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<List<ModelJobList>> GetJobList(final int userId) {

        return Observable.create(new Observable.OnSubscribe<List<ModelJobList>>() {
            @Override
            public void call(final Subscriber<? super List<ModelJobList>> subscriber) {
                beConnector.GetJobList(userId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<ModelJobList>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<ModelJobList> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }


    public Observable<List<ModelMyShifts>> GetAppliedJobs(final int userId) {

        return Observable.create(new Observable.OnSubscribe<List<ModelMyShifts>>() {
            @Override
            public void call(final Subscriber<? super List<ModelMyShifts>> subscriber) {
                beConnector.GetAppliedJobs(userId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<ModelMyShifts>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<ModelMyShifts> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<ModelUpdateJob> UpdateShift(final int jobid, final String extraHours, final String field1,
                                                  final String field2, final String image) {

        return Observable.create(new Observable.OnSubscribe<ModelUpdateJob>() {
            @Override
            public void call(final Subscriber<? super ModelUpdateJob> subscriber) {
                beConnector.UpdateShift(jobid, extraHours, field1, field2, image)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ModelUpdateJob>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(ModelUpdateJob authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<List<ModelDepList>> GetDepList() {

        return Observable.create(new Observable.OnSubscribe<List<ModelDepList>>() {
            @Override
            public void call(final Subscriber<? super List<ModelDepList>> subscriber) {
                beConnector.GetDepList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<ModelDepList>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<ModelDepList> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }



    public Observable<List<ModelHospitalList>> GetHospitalList() {

        return Observable.create(new Observable.OnSubscribe<List<ModelHospitalList>>() {
            @Override
            public void call(final Subscriber<? super List<ModelHospitalList>> subscriber) {
                beConnector.GetHospitalList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<List<ModelHospitalList>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<ModelHospitalList> authResponse) {
                                subscriber.onNext(authResponse);
                            }
                        });
            }
        });
    }


//    public Observable<List<RM_MatchActive>> API_MatchActive() {
//
//        return Observable.create(new Observable.OnSubscribe<List<RM_MatchActive>>() {
//            @Override
//            public void call(final Subscriber<? super List<RM_MatchActive>> subscriber) {
//                beConnector.matchActive()
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(Schedulers.newThread())
//                        .subscribe(new Subscriber<List<RM_MatchActive>>() {
//                            @Override
//                            public void onCompleted() {
//                                subscriber.onCompleted();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                subscriber.onError(e);
//                            }
//
//                            @Override
//                            public void onNext(List<RM_MatchActive> authResponse) {
//                                subscriber.onNext(authResponse);
//                            }
//                        });
//            }
//        });
//    }
//
//
//
//    public Observable<List<RM_BlockList>> API_BlockList() {
//
//        return Observable.create(new Observable.OnSubscribe<List<RM_BlockList>>() {
//            @Override
//            public void call(final Subscriber<? super List<RM_BlockList>> subscriber) {
//                beConnector.BlockList()
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(Schedulers.newThread())
//                        .subscribe(new Subscriber<List<RM_BlockList>>() {
//                            @Override
//                            public void onCompleted() {
//                                subscriber.onCompleted();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                subscriber.onError(e);
//                            }
//
//                            @Override
//                            public void onNext(List<RM_BlockList> authResponse) {
//                                subscriber.onNext(authResponse);
//                            }
//                        });
//            }
//        });
//    }
//
//
//    public Observable<List<RM_CityList>> API_CityList() {
//
//        return Observable.create(new Observable.OnSubscribe<List<RM_CityList>>() {
//            @Override
//            public void call(final Subscriber<? super List<RM_CityList>> subscriber) {
//                beConnector.CityList()
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(Schedulers.newThread())
//                        .subscribe(new Subscriber<List<RM_CityList>>() {
//                            @Override
//                            public void onCompleted() {
//                                subscriber.onCompleted();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                subscriber.onError(e);
//                            }
//
//                            @Override
//                            public void onNext(List<RM_CityList> authResponse) {
//                                subscriber.onNext(authResponse);
//                            }
//                        });
//            }
//        });
//    }
//
//
//    public Observable<RM_MatchPrediction> API_PostMatchPredictions(final String userId,
//                                                                   final String matchId,
//                                                                   final String blockId,
//                                                                   final String innings,
//                                                                   final String matchOver,
//                                                                   final String ball_1,
//                                                                   final String ball_2,
//                                                                   final String ball_3,
//                                                                   final String ball_4,
//                                                                   final String ball_5,
//                                                                   final String ball_6) {
//
//        return Observable.create(new Observable.OnSubscribe<RM_MatchPrediction>() {
//            @Override
//            public void call(final Subscriber<? super RM_MatchPrediction> subscriber) {
//                beConnector.PostMatchPrediction(userId, matchId,blockId, innings,matchOver,ball_1,ball_2,ball_3,ball_4,ball_5,ball_6)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(Schedulers.newThread())
//                        .subscribe(new Subscriber<RM_MatchPrediction>() {
//                            @Override
//                            public void onCompleted() {
//                                subscriber.onCompleted();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                subscriber.onError(e);
//                            }
//
//                            @Override
//                            public void onNext(RM_MatchPrediction authResponse) {
//                                subscriber.onNext(authResponse);
//                            }
//                        });
//            }
//        });
//    }
//

}
