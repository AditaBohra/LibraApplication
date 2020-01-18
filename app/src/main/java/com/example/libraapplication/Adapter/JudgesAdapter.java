package com.example.libraapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.R;

import java.util.ArrayList;

public class JudgesAdapter extends RecyclerView.Adapter<JudgesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> judgesList;
    private CheckListener checkListener;

    public interface CheckListener{
         void onDeselect(ArrayList<String> judgeList);
    }

    public JudgesAdapter(Context context, ArrayList<String> judgesList, CheckListener checkListener){
        this.context = context;
        this.judgesList = judgesList;
        this.checkListener = checkListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_judge_dialog_recyclerview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String judge = judgesList.get(position);
        holder.judgeCheckBox.setText(judge);
        holder.judgeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.judgeCheckBox.isActivated()){
                    judgesList.remove(position);
                    checkListener.onDeselect(judgesList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return judgesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox judgeCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judgeCheckBox = itemView.findViewById(R.id.judge_name_check);
        }
    }
}
