package com.mobiledev183.lawapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiledev183.lawapp.Model.AppointmentModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AppointmentDialogBox extends Dialog {
    private Context mContext;
    private EditText edit_appointment_title;
    private EditText edit_appointment_desc;
    private TextView edit_appointment_date;
    private EditText edit_appointment_clientName;
    private EditText edit_appointment_clientMobile;
    private EditText edit_appointment_clientEmail;
    private Button button_save;
    private Calendar mCalender;
    private AppointmentModel appointmentModel;
    private boolean isFromEdit;
    private AppointmentDismissListener appointmentDismissListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;


    public interface AppointmentDismissListener{
        void onApptDismiss();
    }
    public AppointmentDialogBox(@NonNull Context context, AppointmentDismissListener appointmentDismissListener) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        mContext = context;
        this.appointmentDismissListener = appointmentDismissListener;
    }

    public AppointmentDialogBox(@NonNull Context context, AppointmentModel appointmentModel, AppointmentDismissListener appointmentDismissListener) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        mContext = context;
        this.appointmentModel = appointmentModel;
        isFromEdit = true;
        this.appointmentDismissListener = appointmentDismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.appointment_dialog);
        mCalender = Calendar.getInstance();

        edit_appointment_title = findViewById(R.id.edit_appointment_title);
        edit_appointment_desc = findViewById(R.id.edit_appointment_desc);
        edit_appointment_date = findViewById(R.id.edit_appointment_date);
        edit_appointment_clientName = findViewById(R.id.edit_apt_client_name);
        edit_appointment_clientMobile = findViewById(R.id.edit_apt_client_mb_no);
        edit_appointment_clientEmail = findViewById(R.id.edit_apt_client_email);

        if (appointmentModel != null){
            edit_appointment_title.setText(appointmentModel.getTitle());
            edit_appointment_desc.setText(appointmentModel.getDesc());
            edit_appointment_date.setText(appointmentModel.getDate());
            edit_appointment_clientName.setText(appointmentModel.getClientName());
            edit_appointment_clientMobile.setText(appointmentModel.getClientMbNo());
            edit_appointment_clientEmail.setText(appointmentModel.getClientEmail());
        }


        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance();
                mDatabaseRef = mDatabase.getReference();

                if (checkValidation()) {
                    Toast.makeText(mContext, "Please fill form completely..", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (isFromEdit){
                        mDatabaseRef.child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                                .child("Appointments").child(appointmentModel.getTitle()).removeValue();
                    }
                    else {
                        appointmentModel = new AppointmentModel();
                    }
                    appointmentModel.setTitle(edit_appointment_title.getText().toString());
                    appointmentModel.setDesc(edit_appointment_desc.getText().toString());
                    appointmentModel.setClientEmail(edit_appointment_clientEmail.getText().toString());
                    appointmentModel.setDate(edit_appointment_date.getText().toString());
                    appointmentModel.setClientName(edit_appointment_clientName.getText().toString());
                    appointmentModel.setClientMbNo(edit_appointment_clientMobile.getText().toString());

                    mDatabaseRef.child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                            .child("Appointments").child(appointmentModel.getTitle()).setValue(appointmentModel);
                    clearEditBoxes();
                    dismiss();
                    appointmentDismissListener.onApptDismiss();
                }
            }
        });

        DatePickerDialog.OnDateSetListener date = (view1, year, month, dayOfMonth) -> {
            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, month);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setDateEditText(edit_appointment_date, mCalender);
        };

        edit_appointment_date.setOnClickListener(v -> new DatePickerDialog(Objects.requireNonNull(mContext),
                date, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)).show());

    }

    private void setDateEditText(TextView case_date, Calendar mCalender) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        case_date.setText(sdf.format(mCalender.getTime()));
    }

    private boolean checkValidation() {
        if (edit_appointment_title.getText().toString().isEmpty() || edit_appointment_desc.getText().toString().isEmpty() ||
                edit_appointment_clientName.getText().toString().isEmpty() || edit_appointment_clientMobile.getText().toString().isEmpty()
                || edit_appointment_clientEmail.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void clearEditBoxes() {
        edit_appointment_title.setText("");
        edit_appointment_desc.setText("");
        edit_appointment_date.setText("");
        edit_appointment_clientEmail.setText("");
        edit_appointment_clientName.setText("");
        edit_appointment_clientMobile.setText("");
    }


}
