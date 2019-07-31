package com.adaxiom.subFragments.timesheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.locumset.JobDetail;
import com.adaxiom.locumset.R;
import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelUpdateJob;
import com.adaxiom.utils.SharedPrefrence;

import rx.Subscriber;
import rx.schedulers.Schedulers;


public class FragTimeSheetFive extends Fragment {

    View view;
    TextView tvDone;
    private View avLoading;
    FragTimeSheetOne fragOne;
    FragTimeSheetTwo fragTwo;
    FragTimeSheetThree fragThree;
    FragTimeSheetFour fragFour;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_timesheet_five, container, false);

        setViews();


        return view;
    }


    public void getDataFromFragments(){



    }

    public void setViews(){

        tvDone = view.findViewById(R.id.tvDoneFragFive);
        avLoading = view.findViewById(R.id.avLoadingView);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    public void APIUpdateJob(){

            int userId = SharedPrefrence.getUserId(getActivity());


            avLoading.setVisibility(View.VISIBLE);

            DownloaderManager.getGeneralDownloader().UpdateShift(0,"","","",null)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ModelUpdateJob>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(final Throwable e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    avLoading.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onNext(final ModelUpdateJob modelJobApply) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    avLoading.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


    }




}
