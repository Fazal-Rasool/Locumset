package com.adaxiom.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.adapters.RA_Home;
import com.adaxiom.locumset.JobDetail;
import com.adaxiom.locumset.Login;
import com.adaxiom.locumset.R;
import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.models.ModelJobList;
import com.adaxiom.models.ModelLogin;
import com.adaxiom.network.ApiCalls;
import com.adaxiom.utils.SharedPrefrence;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;


public class FragAllJobs extends Fragment {

    private View view;

    private TextView tv_phone;
    private String card;

    private RecyclerView recyclerView;
    public static RA_Home reAdapter;
    List<ModelJobList> listHome;
    SwipeRefreshLayout swipeContainer;
    private View avLoading;
    SearchView searchViewShop;

    private Subscription getSubscription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_all_jobs, container, false);

        createObjects();
        setViews();
        setListener();
//        API_GetJobsLis();

//        setView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        API_GetJobsLis();
    }

    public void createObjects() {
        listHome = new ArrayList<>();
    }

    public void setViews() {

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerPthFragment);
        avLoading = (View) view.findViewById(R.id.avLoadingView);
    }

    private void setListener() {

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                API_GetJobsLis();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }


    public void setAdapter() {

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        reAdapter = new RA_Home(getActivity(), listHome,
                new RA_Home.RecyclerViewItemClickListener() {
                    @Override
                    public void recyclerViewListClicked(View v, int position) {

                        Intent intent = new Intent(getActivity(), JobDetail.class);

                        intent.putExtra("Title", listHome.get(position).title);
                        intent.putExtra("Price", listHome.get(position).price);
                        intent.putExtra("Dep", listHome.get(position).department);
                        intent.putExtra("Add", listHome.get(position).hospital_name);
                        intent.putExtra("fDate", listHome.get(position).from_date);
                        intent.putExtra("tDate", listHome.get(position).to_date);
                        intent.putExtra("sTime", listHome.get(position).start_time);
                        intent.putExtra("eTime", listHome.get(position).end_time);
                        intent.putExtra("jobId", listHome.get(position).job_id);
                        intent.putExtra("Note", listHome.get(position).note);
                        intent.putExtra("payGrade", listHome.get(position).paygrade);
                        intent.putExtra("Grade", listHome.get(position).grade);
                        intent.putExtra("Email", listHome.get(position).email);
                        intent.putExtra("Phone", listHome.get(position).phone_no);
                        intent.putExtra("flag", "1");
                        intent.putExtra("status", 1);

                        startActivity(intent);
                    }
                });

        recyclerView.setAdapter(reAdapter);
    }


    public void API_GetJobsLis() {



        int userId = SharedPrefrence.getUserId(getActivity());

//        if (getSubscription != null) {
//            return;
//        }

        avLoading.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(true);

        getSubscription = DownloaderManager.getGeneralDownloader().GetJobList(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<ModelJobList>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeContainer.setRefreshing(false);
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final List<ModelJobList> listJob) {
                        listHome.addAll(listJob);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeContainer.setRefreshing(false);
                                avLoading.setVisibility(View.GONE);
                                if (listHome.size() != 0) {
                                    setAdapter();
//                    Toast.makeText(getActivity(), "Data Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

//        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", " Please wait");
//        progressDialog.setCancelable(false);
//
//        int userId = SharedPrefrence.getUserId(getActivity());
//
//        ApiCalls callInterface = ApiClass.getClient().create(ApiCalls.class);
//        Call<List<ModelJobList>> call = callInterface.GetJobList(userId);
//
//        call.enqueue(new Callback<List<ModelJobList>>() {
//            @Override
//            public void onResponse(Call<List<ModelJobList>> call, Response<List<ModelJobList>> response) {
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
//
//                swipeContainer.setRefreshing(false);
//
//                if (response.code() == 200) {
//                    listHome = response.body();
//                    if (listHome.size() != 0) {
//                        setAdapter();
////                    Toast.makeText(getActivity(), "Data Found", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Bad request!!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ModelJobList>> call, Throwable t) {
//                Toast.makeText(getActivity(), "Error while fetching jobs from server", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    public static void doSearch(String query) {
        reAdapter.filter(query);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getActivity().getMenuInflater().inflate(R.menu.home, menu);
//
//        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
//        searchViewShop = (SearchView) myActionMenuItem.getActionView();
//        searchViewShop.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                reAdapter.filter(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                reAdapter.filter(newText);
//                return true;
//            }
//        });
//        return true;
//    }

}



