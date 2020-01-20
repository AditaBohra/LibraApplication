package com.example.libraapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.libraapplication.Model.AppointmentModel;
import com.example.libraapplication.Model.TaskModel;

import java.util.ArrayList;

public class AppointmentDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "appointment_data.sqlite";
    private static String tablename = "appointment";

    public AppointmentDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + tablename + " (title TEXT, description TEXT, date TEXT, client_name TEXT, client_mobile TEXT, client_email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String querry = "DROP TABLE IF EXISTS " + tablename;
        sqLiteDatabase.execSQL(querry);
        onCreate(sqLiteDatabase);
    }

    public void saveData(String title, String desc, String date, String clientName, String clientMobile, String clientEmail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", desc);
        contentValues.put("date", date);
        contentValues.put("client_name", clientName);
        contentValues.put("client_mobile", clientMobile);
        contentValues.put("client_email", clientEmail);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(tablename, null, contentValues);
    }

    public ArrayList<AppointmentModel> getData() {
        String querry = "SELECT * FROM " + tablename;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(querry, null);

        ArrayList<AppointmentModel> arrayList = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                AppointmentModel appointmentModel = new AppointmentModel();
                appointmentModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                appointmentModel.setDesc(cursor.getString(cursor.getColumnIndex("description")));
                appointmentModel.setDate(cursor.getString(cursor.getColumnIndex("date")));
                appointmentModel.setClientName(cursor.getString(cursor.getColumnIndex("client_name")));
                appointmentModel.setClientMbNo(cursor.getString(cursor.getColumnIndex("client_mobile")));
                appointmentModel.setClientEmail(cursor.getString(cursor.getColumnIndex("client_email")));
                arrayList.add(appointmentModel);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return arrayList;
    }
}
