package com.adaxiom.subFragments.timesheet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.locumset.JobDetail;
import com.adaxiom.locumset.R;
import com.adaxiom.locumset.TimeSheet;
import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.models.ModelJobApply;
import com.adaxiom.models.ModelUpdateJob;
import com.adaxiom.utils.SharedPrefrence;
import com.adaxiom.utils.Utilities;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;

import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_BREAK_TIME;
import static com.adaxiom.utils.Constants.PREF_SIGNATURE;
import static com.adaxiom.utils.Constants.PREF_TOTAL_AMOUNT;


public class FragTimeSheetFive extends Fragment {

    View view;
    TextView tvDone, tvTitle, tvGrade, tvAdd, tvDep, tvPrice, tvTotalAmount;
    EditText etTime, etDate;
    ImageView ivSign;
    int jobId=0;
    private View avLoading;
    //    FragTimeSheetThree fragThree;
//    FragTimeSheetFour fragFour;
    TimeSheet timeSheet;
    Utilities utilities = new Utilities();
    int totalAmount=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_timesheet_five, container, false);

        setViews();
        getDataFromFragments();


        return view;
    }


    public void getDataFromFragments() {

        String title = timeSheet.strTitle;
        String toDate = timeSheet.strToDate;
        String fromDate = timeSheet.strFromDate;
        String startTime = timeSheet.strStartTime;
        String endTime = timeSheet.strEndTime;
        String address = timeSheet.strAdd;
        String grade = timeSheet.strGrade;
        String department = timeSheet.strDep;
        String pricePerHour = timeSheet.strPrice;
        totalAmount = timeSheet.total_amount;
        String breakTime = Prefs.getString(PREF_BREAK_TIME, "00");
//        String signature = Prefs.getString(PREF_SIGNATURE,"");


//        Toast.makeText(getActivity(), newSign, Toast.LENGTH_SHORT).show();

//        String newSign = utilities.getImagePath();
        String path = timeSheet.getImagePath();
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivSign.setImageBitmap(myBitmap);
        }


        tvTitle.setText(title);
        tvGrade.setText(grade);
        tvDep.setText(department);
        tvAdd.setText(address);
        tvPrice.setText("£ " + pricePerHour + " per hour");
        tvTotalAmount.setText("£ " + totalAmount);
        etDate.setText(fromDate+"-"+toDate);
        etTime.setText(startTime+" - "+endTime);


    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            Toast.makeText(getActivity(), "5", Toast.LENGTH_SHORT).show();
//            String newSign = utilities.getImagePath();
//            File imgFile = new File(newSign);
//            if (imgFile.exists()) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                ivSign.setImageBitmap(myBitmap);
//            }


        }
    }


    public void setViews() {

        tvDone = view.findViewById(R.id.tvDoneFragFive);
        tvTitle = view.findViewById(R.id.tvTitleFragFive);
        tvGrade = view.findViewById(R.id.tvGradeFragFive);
        tvAdd = view.findViewById(R.id.tvAddFragFive);
        tvDep = view.findViewById(R.id.tvDepFragFive);
        tvPrice = view.findViewById(R.id.tvPriceFragFive);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmountFragFive);
        etTime = view.findViewById(R.id.etTimeFragFive);
        etDate = view.findViewById(R.id.etDateFragFive);
        ivSign = view.findViewById(R.id.ivSignFragFive);


        avLoading = view.findViewById(R.id.avLoadingView);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIUpdateJob();
            }
        });

    }


    public void APIUpdateJob() {

        int userId = SharedPrefrence.getUserId(getActivity());
        jobId = timeSheet.jobId;


        avLoading.setVisibility(View.VISIBLE);

        DownloaderManager.getGeneralDownloader().UpdateShift(jobId, "0", totalAmount+"", "0")
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
                    public void onNext(final ModelUpdateJob model) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), model.message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }


}
