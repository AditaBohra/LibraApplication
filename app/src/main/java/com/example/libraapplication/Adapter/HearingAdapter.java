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
        holder.party1Text.setText(hearingModel.getParty1());
        holder.party2Text.setText(hearingModel.getParty2());
        holder.t1.setText(hearingModel.getText1());
        holder.t2.setText(hearingModel.getText2());
        holder.t3.setText(hearingModel.getText3());

        holder.itemView.setOnClickListener( v -> {
            mListener.onItemClick(hearingModel);
        });

    }

    @Override
    public int getItemCount() {
        return mCaseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView party1Text;
        TextView party2Text;
        TextView t1;
        TextView t2;
        TextView t3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            party1Text = itemView.findViewById(R.id.state_text_name);
            party2Text = itemView.findViewById(R.id.vs_text_name);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);

        }
    }

}
