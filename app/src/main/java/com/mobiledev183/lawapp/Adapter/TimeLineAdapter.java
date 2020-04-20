package com.mobiledev183.lawapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiledev183.lawapp.Model.Timeline;
import com.mobiledev183.lawapp.R;

import java.util.ArrayList;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Timeline> timelineArrayList;

    public TimeLineAdapter(Context context, ArrayList<Timeline> timelineArrayList) {
        this.context = context;
        this.timelineArrayList = timelineArrayList;
        Log.d("TimelineData = ", timelineArrayList.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.timeline_recyclerview, null);
        return new ViewHolder(constraintLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Timeline timeline = timelineArrayList.get(position);
        holder.date.setText(timeline.getDate());
        holder.room_name.setText(timeline.getRoom_name());
        holder.judge_name.setText(timeline.getJudge_name());
    }

    @Override
    public int getItemCount() {
        return timelineArrayList.size();
    }

    public class ViewHolder extends HearingAdapter.ViewHolder {

        private TextView date;
        private TextView room_name;
        private TextView judge_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.caseDetails_date_text);
            room_name = itemView.findViewById(R.id.room_text_name);
            judge_name = itemView.findViewById(R.id.judge_text_name);
        }
    }
}
