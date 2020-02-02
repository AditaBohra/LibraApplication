package com.example.libraapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.Model.HearingModel;
import com.example.libraapplication.R;

import java.util.List;

public class HearingAdapter extends RecyclerView.Adapter<HearingAdapter.ViewHolder> {
    private List<CaseModel> mCaseModelList;
    private Context mContext;
    private final OnItemClickListener mListener;
    private List<HearingModel> mHearingModelList;
    private CaseModel mCaseModel;

    public HearingAdapter(Context context, List<CaseModel> caseModelList, OnItemClickListener listener) {
        mContext = context;
        mCaseModelList = caseModelList;
        mListener = listener;

        for(CaseModel caseModel: mCaseModelList){
            mCaseModel = caseModel;
            mHearingModelList = caseModel.getHearingModels();
        }

    }

    public interface OnItemClickListener {
        void onItemClick(CaseModel caseModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.hearing_fragment_recyclerview, null);
        return new ViewHolder(constraintLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            HearingModel hearingModel =  mHearingModelList.get(position);
                if (hearingModel != null) {
                    holder.category.setText(hearingModel.getCategory());
                    holder.court_name_tv.setText(mCaseModel.getCourtName());
                    holder.date_tv.setText(hearingModel.getHearing_date());
                    holder.party1_tv.setText(mCaseModel.getParty1());
                    holder.party2_tv.setText(mCaseModel.getParty2());
                    holder.case_no_tv.setText(hearingModel.getCase_no());
                }


        holder.itemView.setOnClickListener(v -> {
            mListener.onItemClick(mCaseModel);
        });

    }

    @Override
    public int getItemCount() {
        if(mHearingModelList != null) {
            return mHearingModelList.size();
        }
        else {
            return mCaseModelList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView party1_tv;
        TextView party2_tv;
        TextView court_name_tv;
        TextView case_no_tv;
        TextView date_tv;
        TextView category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            party1_tv = itemView.findViewById(R.id.party_1_text);
            party2_tv = itemView.findViewById(R.id.party_2_text);
            date_tv = itemView.findViewById(R.id.date_text_hearing);
            court_name_tv = itemView.findViewById(R.id.court_name_text_hearing);
            case_no_tv = itemView.findViewById(R.id.case_no_hearing);
            category = itemView.findViewById(R.id.radio_group_category_hearing);

        }
    }


}
