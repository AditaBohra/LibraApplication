package com.example.libraapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.R;

import java.util.ArrayList;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CaseModel> caseList;
    private CaseClickListener caseClickListener;

    public interface CaseClickListener{
        public void onCaseClick(CaseModel caseModel);
    }

    public CaseAdapter(Context context, ArrayList<CaseModel> caseList, CaseClickListener caseClickListener) {
        this.context = context;
        this.caseList = caseList;
        this.caseClickListener = caseClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.case_fragment_recyclerview, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CaseModel caseModel = caseList.get(position);
        holder.case_no_text.setText(caseModel.getCaseNo());
        holder.party1_text.setText(caseModel.getParty1());
        holder.party2_text.setText(caseModel.getParty2());
        holder.courtName_text.setText(caseModel.getCourtName());
        if (caseModel.getStatus().contains("Dispose")){
            holder.status_text.setBackground(ContextCompat.getDrawable(this.context,R.drawable.rounded_button));
            holder.status_text.setTextColor(Color.WHITE);
        }
        else {
            holder.status_text.setBackground(ContextCompat.getDrawable(this.context,R.drawable.rounded_button_grey));
        }
        holder.status_text.setText(caseModel.getStatus());
        holder.hearingDate_text.setText("Next Hearing: "+caseModel.getHearingDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caseClickListener.onCaseClick(caseModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView party1_text;
        private TextView party2_text;
        private TextView status_text;
        private TextView courtName_text;
        private TextView hearingDate_text;
        private TextView case_no_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            party1_text = itemView.findViewById(R.id.party1_text);
            party2_text = itemView.findViewById(R.id.party2_text);
            status_text = itemView.findViewById(R.id.status_text);
            courtName_text = itemView.findViewById(R.id.court_name_text);
            hearingDate_text = itemView.findViewById(R.id.hearing_date_text);
            case_no_text = itemView.findViewById(R.id.case_no_text);
        }
    }
}
