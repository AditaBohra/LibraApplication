package com.example.libraapplication.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.AppointmentDialogBox;
import com.example.libraapplication.Database.AppointmentDBHelper;
import com.example.libraapplication.R;
import com.example.libraapplication.Adapter.TaskListAdapter;
import com.example.libraapplication.Model.TaskModel;

import java.util.ArrayList;

public class AppointmentFragment extends Fragment {

    private TaskListAdapter mAppointmentListAdapter;
    private RecyclerView mAppointmentRecyclerView;
    private Button mAddAppointmentButton;
    private ArrayList<TaskModel> mAppointmentList;
    private AppointmentDBHelper dbHelper;
    private AppointmentDialogBox mDialogBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.appointment_fragment,null);

        mAppointmentRecyclerView = view.findViewById(R.id.appointmentlist_recyclerview);
        mAddAppointmentButton = view.findViewById(R.id.add_appointment_button);
        mAppointmentList = new ArrayList<>();
        dbHelper = new AppointmentDBHelper(getActivity());
        mDialogBox = new AppointmentDialogBox(getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]
                    {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},1);
        }

        //Getting task data from screen..
        try{
            getTaskData();
        }catch (Exception e){

        }

        mAddAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogBox.show();
            }
        });

        mDialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    getTaskData();
                }
                catch (Exception e){
                }
            }
        });
        return view;
    }

    private void getTaskData() {
        mAppointmentList = dbHelper.getData();
        if (!mAppointmentList.isEmpty()) {
            mAppointmentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            mAppointmentListAdapter = new TaskListAdapter(getActivity(), mAppointmentList);
            mAppointmentRecyclerView.setAdapter(mAppointmentListAdapter);
        }
    }
}
