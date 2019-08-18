package com.example.chauffeurondemand.com.dbtask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class dmanager
{
    dhelper helper;
    Context context;
    public dmanager(FragmentActivity context)
    {
        this.context = context;
        helper = new dhelper(context,dconstant.DB_NAME,null,dconstant.DB_VERSION);


    }
    public SQLiteDatabase OpenDb()
    {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        return sqLiteDatabase;

    }
    public void closeDb()
    {
        if(helper!=null)
            helper.close();

    }



}
