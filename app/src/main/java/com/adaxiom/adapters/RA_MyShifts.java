package com.adaxiom.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adaxiom.locumset.R;
import com.adaxiom.models.ModelMyShifts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RA_MyShifts extends RecyclerView.Adapter<RA_MyShifts.ViewHolder> {

    private Activity activity;
    public List<ModelMyShifts> list;
    public List<ModelMyShifts> dummyList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewItemClickListener onItemClickListener;


    public interface RecyclerViewItemClickListener {

        void recyclerViewListClicked(View v, int position);
    }

    public RA_MyShifts(Activity activity,
                       List<ModelMyShifts> list,
                       RecyclerViewItemClickListener onItemClickListener) {

        this.list = list;
        dummyList = new ArrayList<>();
        this.dummyList.addAll(list);
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_my_shifts, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;


    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        Date fromDate = parseDate(list.get(position).from_date);
        Date toDate = parseDate(list.get(position).to_date);
        String startTime = parseTime(list.get(position).start_time);
        String endTime = parseTime(list.get(position).end_time);
        String f_Day = "", f_dayNum = "", f_mnth = "", t_Day = "", t_dayNum = "", t_mnth = "" ;

        if (fromDate != null) {
            f_Day = (String) DateFormat.format("EE", fromDate); // Thursday
            f_dayNum = (String) DateFormat.format("dd", fromDate); // 20
            f_mnth = (String) DateFormat.format("MMM", fromDate); // Jun
        }

        if (toDate != null) {
            t_Day = (String) DateFormat.format("EE", toDate); // Thursday
            t_dayNum = (String) DateFormat.format("dd", toDate); // 20
            t_mnth = (String) DateFormat.format("MMM", toDate); // Jun
        }

//        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
//        String year         = (String) DateFormat.format("yyyy", date);

        String comFirst = f_dayNum + f_mnth;
        String comSec = t_dayNum + t_mnth;

        if (comFirst.equalsIgnoreCase(comSec)) {
            viewHolder.tvDate.setText(f_dayNum + " " + f_mnth);
//            viewHolder.tvDay.setText(f_Day);
            viewHolder.tvTime.setText(startTime + "-" + endTime);
        } else {
            viewHolder.tvDate.setText(f_dayNum + " to " + t_dayNum + " " + f_mnth);
//            viewHolder.tvDay.setText(f_Day + " - " + t_Day);
            viewHolder.tvTime.setText(startTime + "-" + endTime);
        }


        viewHolder.tvTitle.setText(list.get(position).title);
        viewHolder.tvPrice.setText("£" + list.get(position).price);
        viewHolder.tvHospitalName.setText(list.get(position).hospital_name);
        viewHolder.tvDep.setText(list.get(position).department);

        if(list.get(position).status == 0){
            viewHolder.panel.setBackgroundResource( R.drawable.shift_pending);
            viewHolder.tvLable.setBackgroundResource(R.drawable.lable_pending);
            viewHolder.tvLable.setText("Pending");
        }else if(list.get(position).status==1){
            viewHolder.panel.setBackgroundResource( R.drawable.shift_approved);
            viewHolder.tvLable.setBackgroundResource(R.drawable.lable_approved);
            viewHolder.tvLable.setText("Approved");

        }else if(list.get(position).status==3){
            viewHolder.panel.setBackgroundResource( R.drawable.shift_cancel);
            viewHolder.tvLable.setBackgroundResource(R.drawable.lable_cancel);
            viewHolder.tvLable.setText("Canceled");

        }else if(list.get(position).status==4){

            viewHolder.panel.setBackgroundResource( R.drawable.border_my_shift_timesheet);
            viewHolder.tvLable.setBackgroundResource(R.drawable.lable_timesheet);
            viewHolder.tvLable.setText("TimeSheet");

        }else if(list.get(position).status==5){

            viewHolder.panel.setBackgroundResource( R.drawable.border_my_shift_timesheet);
            viewHolder.tvLable.setBackgroundResource(R.drawable.lable_timesheet);
            viewHolder.tvLable.setText("TimeSheet");

        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }


    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvPrice, tvHospitalName, tvDep, tvDate, tvTime, tvLable;
        View panel;


        public ViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvRowJobsTitle);
            tvPrice = (TextView) view.findViewById(R.id.tvRowJobsPrice);
            tvHospitalName = (TextView) view.findViewById(R.id.tvRowJobsHospitalName);
            tvDep = (TextView) view.findViewById(R.id.tvRowJobsDep);
            tvDate = (TextView) view.findViewById(R.id.tvRowJobsDate);
//            tvDay = (TextView) view.findViewById(R.id.tvRowJobsDay);
            tvTime = (TextView) view.findViewById(R.id.tvRowJobsTime);
            tvLable = (TextView) view.findViewById(R.id.tvMyShiftLable);
            panel = (View) view.findViewById(R.id.rowMyShiftPanel);

            view.setOnClickListener(this);

//            rating =(RatingBar) view.findViewById(R.id.chefRating);
//            tvTitle = (TextView) view.findViewById(R.id.tv_chef_name);
//            ivCheff = (ImageView) view.findViewById(R.id.chefImageView);


        }

        @Override
        public void onClick(View v) {

            onItemClickListener.recyclerViewListClicked(v, this.getLayoutPosition());

        }
    }


    public void filter(int status) {
        list.clear();
        if (status==1) {
            list.addAll(dummyList);
        } else if(status ==2){
//            text = text.toLowerCase();
            for (ModelMyShifts item : dummyList) {
                if (item.status == 1) {
                    list.add(item);
                }
            }
        }else if(status == 3){
            for (ModelMyShifts item : dummyList) {
                if (item.status == 0) {
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


    public Date parseDate(String strDate) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String parseTime(String strDate) {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(new Date());

        return time;
    }


}
