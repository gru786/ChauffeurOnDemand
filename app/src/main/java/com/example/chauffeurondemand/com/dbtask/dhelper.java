package com.example.chauffeurondemand.com.dbtask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class dhelper extends SQLiteOpenHelper {
    Context context;
    public dhelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(dconstant.DRIVER_DETAIL_QUERY);
        Toast.makeText(context, "driver detail created", Toast.LENGTH_SHORT).show();
        db.execSQL(dconstant.CLIENT_DETAIL_QUERY);
        Toast.makeText(context, "client detail created", Toast.LENGTH_SHORT).show();
        db.execSQL(dconstant.FEEDBACK_INFO_QUERY);
        Toast.makeText(context, "feedback created", Toast.LENGTH_SHORT).show();
        db.execSQL(dconstant.REQUEST_DRIVER_QUERY);
        Toast.makeText(context, "request created", Toast.LENGTH_SHORT).show();
        db.execSQL(dconstant.ASSIGN_DRIVER_QUERY);
        Toast.makeText(context, "assign created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
