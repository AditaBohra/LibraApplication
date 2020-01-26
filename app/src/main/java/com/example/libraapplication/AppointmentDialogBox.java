package com.example.libraapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.libraapplication.Database.AppointmentDBHelper;

public class AppointmentDialogBox extends Dialog {
    private Context mContext;
    private EditText edit_appointment_title;
    private EditText edit_appointment_desc;
    private EditText edit_appointment_date;
    private EditText edit_appointment_clientName;
    private EditText edit_appointment_clientMobile;
    private EditText edit_appointment_clientEmail;
    private Button button_save;
    private AppointmentDBHelper dbHelper;
    private CalenderBox mCalenderBox;
    private String calenderDate;

    public AppointmentDialogBox(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.appointment_dialog);
        mCalenderBox = new CalenderBox(mContext);

        dbHelper = new AppointmentDBHelper(mContext);
        dbHelper.getReadableDatabase();

        edit_appointment_title = findViewById(R.id.edit_appointment_title);
        edit_appointment_desc = findViewById(R.id.edit_appointment_desc);
        edit_appointment_date = findViewById(R.id.edit_appointment_date);
        edit_appointment_clientName = findViewById(R.id.edit_apt_client_name);
        edit_appointment_clientMobile = findViewById(R.id.edit_apt_client_mb_no);
        edit_appointment_clientEmail = findViewById(R.id.edit_apt_client_email);


        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    Toast.makeText(mContext, "Please fill form completely..", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                }
                dbHelper.saveData(edit_appointment_title.getText().toString(), edit_appointment_desc.getText().toString()
                        , calenderDate, edit_appointment_clientName.getText().toString(), edit_appointment_clientMobile.getText().toString(),
                        edit_appointment_clientEmail.getText().toString());
                clearEditBoxes();
                dismiss();
            }
        });

        edit_appointment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalenderBox.show();
                Window window = mCalenderBox.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            }
        });

        mCalenderBox.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("datestorage", Context.MODE_PRIVATE);
                calenderDate = sharedPreferences.getString("date", null);
                edit_appointment_date.setText(calenderDate);
            }
        });

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
