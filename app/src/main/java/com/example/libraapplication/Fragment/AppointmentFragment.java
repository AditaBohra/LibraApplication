package com.example.libraapplication.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Adapter.AppointmentListAdapter;
import com.example.libraapplication.AppointmentDialogBox;
import com.example.libraapplication.Database.AppointmentDBHelper;
import com.example.libraapplication.Model.AppointmentModel;
import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AppointmentFragment extends Fragment implements AppointmentListAdapter.AppointmentClickListener, AppointmentDialogBox.AppointmentDismissListener {

    private AppointmentListAdapter mAppointmentListAdapter;
    private RecyclerView mAppointmentRecyclerView;
    private Button mAddAppointmentButton;
    private ArrayList<AppointmentModel> mAppointmentList;
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
                createAppointment();
            }
        });

        return view;
    }

    private void createAppointment() {
        mDialogBox = new AppointmentDialogBox(getActivity(),this);
        openAppointmentDialog();
    }

    private void openAppointmentDialog() {
        Window window = mDialogBox.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mDialogBox.show();
    }

    private void getTaskData() {
        mAppointmentList = dbHelper.getData();
        if (!mAppointmentList.isEmpty()) {

            Collections.sort(mAppointmentList,Collections.reverseOrder(new Comparator<AppointmentModel>() {
                @Override
                public int compare(AppointmentModel appointmentModel, AppointmentModel t1) {
                    return appointmentModel.getDate().compareTo(t1.getDate());
                }
            }));

            mAppointmentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            mAppointmentListAdapter = new AppointmentListAdapter(getActivity(), mAppointmentList,this);
            mAppointmentRecyclerView.setAdapter(mAppointmentListAdapter);
        }
    }

    @Override
    public void onEditClick(AppointmentModel appointmentModel) {
        mDialogBox = new AppointmentDialogBox(getActivity(), appointmentModel,this);
        openAppointmentDialog();
    }

    @Override
    public void onDeleteClick(AppointmentModel appointmentModel) {
        dbHelper.deleteAppointmentData(appointmentModel.getTitle());
        try {
            getTaskData();
        }
        catch (Exception e){

        }
    }

    @Override
    public void onApptDismiss() {
        try {
            getTaskData();
        }
        catch (Exception e){

        }
    }
}
