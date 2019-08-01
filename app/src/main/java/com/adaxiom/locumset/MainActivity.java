package com.adaxiom.locumset;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.adaxiom.adapters.RA_Home;
import com.adaxiom.fragments.FragAllJobs;
import com.adaxiom.fragments.FragMyShifts;
import com.adaxiom.fragments.FragMyProfile;
import com.adaxiom.utils.SharedPrefrence;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    private AlertDialog alertDialog;

    android.support.v4.app.FragmentManager mFragmentManager;
    android.support.v4.app.FragmentTransaction mFragmentTransaction;

    private SearchView searchViewShop;
    public RA_Home reAdapter;
    MenuItem menuItemSearch, menuItemLogout, menuItemFilter;
    public int tabNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
//        setSupportActionBar(toolbar);

        loadFragment(new FragAllJobs());
        setViews();

    }

    private void setViews() {

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
//        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_find_jobs:
                                tabNum = 1;
                                loadFragment(new FragAllJobs());
                                setToolbarTitle("Available Jobs");
                                return true;
                            case R.id.nav_my_shifts:
                                tabNum = 2;
                                loadFragment(new FragMyShifts());
                                setToolbarTitle("My Shifts");
                                return true;
                            case R.id.nav_time_sheet:
                                tabNum = 3;
//                                Intent intent = new Intent(MainActivity.this, TimeSheet.class);
//                                startActivity(intent);
//                                loadFragment(new FragTimeSheet());
//                                setToolbarTitle("Time Sheet");
                                return true;
                            case R.id.nav_my_profile:
                                tabNum = 4;
                                loadFragment(new FragMyProfile());
                                setToolbarTitle("My Profile");
                                return true;
                        }
                        return false;
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        menuItemSearch = menu.findItem(R.id.action_search);
        menuItemLogout = menu.findItem(R.id.action_logout);
        menuItemFilter = menu.findItem(R.id.action_filter);

        searchViewShop = (SearchView) menuItemSearch.getActionView();
        searchViewShop.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                reAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FragAllJobs.doSearch(newText);
                return true;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
//                SharedPrefrenceStorage.INSTANCE.setIsLogin(MainActivity.this, "0");
                SharedPrefrence.setIsLogin(MainActivity.this, "0");
                startActivity(new Intent(MainActivity.this, Login.class));
                this.finish();
                break;
            case R.id.action_filter:
                showFilterAlert();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setToolbarTitle(String title) {
//        toolbar.setTitle(title);
        setTitle(title);
        switch (tabNum) {
            case 1:
                menuItemSearch.setVisible(true);
                menuItemLogout.setVisible(false);
                menuItemFilter.setVisible(false);
                break;
            case 2:
                menuItemSearch.setVisible(false);
                menuItemLogout.setVisible(false);
                menuItemFilter.setVisible(true);
                break;
            case 3:
                menuItemSearch.setVisible(true);
                menuItemLogout.setVisible(false);
                menuItemFilter.setVisible(false);
                break;
            case 4:
                menuItemSearch.setVisible(false);
                menuItemLogout.setVisible(true);
                menuItemFilter.setVisible(false);
                break;
        }
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    public void showFilterAlert() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_filter, null);
        dialogBuilder.setView(dialogView);

        RadioButton rbAll = (RadioButton) dialogView.findViewById(R.id.rb_all);
        RadioButton rbPending = (RadioButton) dialogView.findViewById(R.id.rb_pending);
        RadioButton rbApproved = (RadioButton) dialogView.findViewById(R.id.rb_approved);

        String check = SharedPrefrence.getFilter(MainActivity.this);
        if (check.equalsIgnoreCase("0")) {
            rbAll.setChecked(true);
        } else if(check.equalsIgnoreCase("1")) {
            rbPending.setChecked(true);
        }else{
            rbApproved.setChecked(true);
        }

        rbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefrence.setFilter(MainActivity.this,"0");
                alertDialog.dismiss();
                FragMyShifts.doSearch(1);

            }
        });

        rbApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefrence.setFilter(MainActivity.this,"2");
                alertDialog.dismiss();
                FragMyShifts.doSearch(2);
            }
        });

        rbPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefrence.setFilter(MainActivity.this, "1");
                alertDialog.dismiss();
                FragMyShifts.doSearch(3);
            }
        });



        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }



}
