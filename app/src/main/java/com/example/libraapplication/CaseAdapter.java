package com.example.libraapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.ViewHolder> {
    private ArrayList<CaseModel> mCaseList;
    private Context mContext;

    public CaseAdapter(Context context, ArrayList<CaseModel> caseList) {
        mContext = context;
        mCaseList = caseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.cardview_layout, null);
        return new ViewHolder(constraintLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CaseModel caseModel = mCaseList.get(position);
        holder.party1Text.setText(caseModel.getParty1());
        holder.party2Text.setText(caseModel.getParty2());
        holder.t1.setText(caseModel.getText1());
        holder.t2.setText(caseModel.getText2());
        holder.t3.setText(caseModel.getText3());

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
