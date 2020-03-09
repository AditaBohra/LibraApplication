package com.example.libraapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class ProgressDialogData extends Dialog {

    public ProgressDialogData(@NonNull Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.please_wait_progress_dialog);
        setTitle("Please Wait..");
        setCancelable(false);
    }
}
