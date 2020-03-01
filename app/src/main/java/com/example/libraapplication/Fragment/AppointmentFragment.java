package com.example.libraapplication.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
import com.example.libraapplication.Model.AppointmentModel;
import com.example.libraapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class AppointmentFragment extends Fragment implements AppointmentListAdapter.AppointmentClickListener, AppointmentDialogBox.AppointmentDismissListener {

    private AppointmentListAdapter mAppointmentListAdapter;
    private RecyclerView mAppointmentRecyclerView;
    private ArrayList<AppointmentModel> mAppointmentList;
    private AppointmentDialogBox mDialogBox;
    private FirebaseDatabase mDatabase;
    private AppointmentModel mAppointmentModel;
    private static final String TAG = AppointmentFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.appointment_fragment,null);

        mAppointmentRecyclerView = view.findViewById(R.id.appointmentlist_recyclerview);
        Button addAppointmentButton = view.findViewById(R.id.add_appointment_button);
        mAppointmentList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]
                    {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},1);
        }

        //Getting task data from screen..

            getAppointmentData();

        addAppointmentButton.setOnClickListener(view1 -> createAppointment());

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

    private void getAppointmentData() {
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Appointments");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAppointmentList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    mAppointmentModel = dataSnapshot1.getValue(AppointmentModel.class);
                    mAppointmentList.add(mAppointmentModel);

                }
                if (!mAppointmentList.isEmpty()) {

                    Collections.sort(mAppointmentList,Collections.reverseOrder(new Comparator<AppointmentModel>() {
                        @Override
                        public int compare(AppointmentModel appointmentModel, AppointmentModel t1) {
                            return appointmentModel.getDate().compareTo(t1.getDate());
                        }
                    }));


                    mAppointmentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    mAppointmentListAdapter = new AppointmentListAdapter(getActivity(), mAppointmentList,AppointmentFragment.this);
                    mAppointmentRecyclerView.setAdapter(mAppointmentListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });


    }

    @Override
    public void onEditClick(AppointmentModel appointmentModel) {
        mDialogBox = new AppointmentDialogBox(getActivity(), appointmentModel,this);
        openAppointmentDialog();
    }

    @Override
    public void onDeleteClick(AppointmentModel appointmentModel) {
        mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Appointments").child(appointmentModel.getTitle()).removeValue();
        mAppointmentList.remove(appointmentModel);
        mAppointmentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onApptDismiss() {
            getAppointmentData();
    }
}
