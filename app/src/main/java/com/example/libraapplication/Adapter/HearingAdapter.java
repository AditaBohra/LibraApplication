package com.example.libraapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Model.HearingModel;
import com.example.libraapplication.R;

import java.util.ArrayList;

public class HearingAdapter extends RecyclerView.Adapter<HearingAdapter.ViewHolder> {
    private ArrayList<HearingModel> mCaseList;
    private Context mContext;
    private final OnItemClickListener mListener;

    public HearingAdapter(Context context, ArrayList<HearingModel> caseList, OnItemClickListener listener) {
        mContext = context;
        mCaseList = caseList;
        mListener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(HearingModel hearingModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.hearing_fragment_recyclerview, null);
        return new ViewHolder(constraintLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HearingModel hearingModel = mCaseList.get(position);
        if(hearingModel != null) {
            holder.room_tv.setText(hearingModel.getCategory());
            holder.judge_tv.setText(hearingModel.getJudge_name());
            holder.date_tv.setText(hearingModel.getHearing_date());
        }

        holder.itemView.setOnClickListener( v -> {
            mListener.onItemClick(hearingModel);
        });

    }

    @Override
    public int getItemCount() {
        return mCaseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView room_tv;
        TextView judge_tv;
        TextView date_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            room_tv = itemView.findViewById(R.id.room_text_name_hearing);
            judge_tv = itemView.findViewById(R.id.judge_text_name_hearing);
            date_tv = itemView.findViewById(R.id.hearing_date);

        }
    }


}
