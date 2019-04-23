package com.adaxiom.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.locumset.R;
import com.adaxiom.models.ModelJobList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RA_Home extends RecyclerView.Adapter<RA_Home.ViewHolder> {

    private Activity activity;
    public List<ModelJobList> list;
    public List<ModelJobList> dummyList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewItemClickListener onItemClickListener;


    public interface RecyclerViewItemClickListener {

        void recyclerViewListClicked(View v, int position);
    }

    public RA_Home(Activity activity,
                   List<ModelJobList> list,
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
        View view = inflater.inflate(R.layout.row_main_list, viewGroup, false);
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
            viewHolder.tvDay.setText(f_Day);
            viewHolder.tvTime.setText(startTime + "-" + endTime);
        } else {
            viewHolder.tvDate.setText(f_dayNum + " to " + t_dayNum + " " + f_mnth);
            viewHolder.tvDay.setText(f_Day + " - " + t_Day);
            viewHolder.tvTime.setText(startTime + "-" + endTime);
        }


        viewHolder.tvTitle.setText(list.get(position).title);
        viewHolder.tvPrice.setText("£" + list.get(position).price);
        viewHolder.tvHospitalName.setText(list.get(position).hospital_name);
        viewHolder.tvDep.setText(list.get(position).department);
        viewHolder.tvGrade.setText(list.get(position).grade);
//        viewHolder.tvGrade.setVisibility(View.GONE);


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

        TextView tvTitle, tvPrice, tvHospitalName, tvDep, tvDate, tvDay, tvTime, tvGrade;


        public ViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvRowJobsTitle);
            tvPrice = (TextView) view.findViewById(R.id.tvRowJobsPrice);
            tvHospitalName = (TextView) view.findViewById(R.id.tvRowJobsHospitalName);
            tvDep = (TextView) view.findViewById(R.id.tvRowJobsDep);
            tvDate = (TextView) view.findViewById(R.id.tvRowJobsDate);
            tvDay = (TextView) view.findViewById(R.id.tvRowJobsDay);
            tvTime = (TextView) view.findViewById(R.id.tvRowJobsTime);
            tvGrade = (TextView) view.findViewById(R.id.tvRowJobsGrade);

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


    public void filter(String text) {
        list.clear();
        if (text.isEmpty()) {
            list.addAll(dummyList);
        } else {
            text = text.toLowerCase();
            for (ModelJobList item : dummyList) {
                if (item.title.toLowerCase().contains(text)) {
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
