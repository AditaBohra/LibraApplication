package com.example.libraapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Model.AppointmentModel;
import com.example.libraapplication.Model.TaskModel;
import com.example.libraapplication.R;

import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<AppointmentModel> mTaskList;


    public AppointmentListAdapter(Context context, ArrayList<AppointmentModel> taskList) {
        mContext = context;
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.appointment_recyclerview, null);
        return new ViewHolder(constraintLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentModel appointmentModel = mTaskList.get(position);
        holder.title.setText("Title: " + appointmentModel.getTitle());
        holder.description.setText("Task Description: " + appointmentModel.getDesc());
        holder.mydate.setText(appointmentModel.getDate());
        holder.clientName.setText("Client Name : " + appointmentModel.getClientName());
        holder.clientMbNo.setText("Client Mobile : " + appointmentModel.getClientMbNo());
        holder.clientEmail.setText("Client Email : " + appointmentModel.getClientEmail());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView mydate;
        private TextView clientName;
        private TextView clientMbNo;
        private TextView clientEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            description = itemView.findViewById(R.id.display_task_desc_text);
            mydate = itemView.findViewById(R.id.display_date_text);
            clientName = itemView.findViewById(R.id.display_apt_clientname_text);
            clientMbNo = itemView.findViewById(R.id.display_apt_clientmbno_text);
            clientEmail = itemView.findViewById(R.id.display_apt_client_email_text);
        }
    }
}