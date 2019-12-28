package com.example.libraapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CalenderBox extends Dialog {
    private Context mContext;

    public CalenderBox(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    private CalendarView calendarView;
    private Button saveDate;
    private TextView textDate;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calender_fragment);

        calendarView = findViewById(R.id.calenderView);
        saveDate = findViewById(R.id.btnSaveDate);
        textDate = findViewById(R.id.selectedDateText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = (i1 + 1) + "/" + i2 + "/" + i;
                textDate.setText(date);
            }
        });


        saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("datestorage",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("date",date);
                editor.commit();
                dismiss();
            }
        });

    }
}
