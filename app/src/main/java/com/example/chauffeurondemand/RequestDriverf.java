package com.example.chauffeurondemand;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chauffeurondemand.com.dbtask.dconstant;
import com.example.chauffeurondemand.com.dbtask.dmanager;

import java.util.ArrayList;
import java.util.Calendar;

public class RequestDriverf extends Fragment
{
    Spinner clientId;
    TextView date;
    EditText fromTime,toTime;
    Button btnsubmit,btncancel;
    ArrayList<String> clist;
    SQLiteDatabase sq;
    dmanager manager;
    DatePickerDialog pickerDialog;
    String date2,cid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.request_driverf,null,false);
        manager = new dmanager(getActivity());
        sq = manager.OpenDb();
        clist = new ArrayList<>();


        clientId = view.findViewById(R.id.clientspin);
        populateSpinner();
        date = view.findViewById(R.id.date);
        Calendar calendar=Calendar.getInstance();
        final int tmonth=calendar.get(Calendar.MONTH);
        final int tyear=calendar.get(Calendar.YEAR);
        final int tday=calendar.get(Calendar.DATE);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog=new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date2=dayOfMonth+"/"+(month+1)+"/"+year;
                        date.setText(date2);
                        //Toast.makeText(getActivity(), "date is"+date2, Toast.LENGTH_SHORT).show();
                    }
                }, tyear, tmonth, tday);

                pickerDialog.show();


            }
        });



        fromTime = view.findViewById(R.id.fromTime);
        toTime = view.findViewById(R.id.toTime);

        clientId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cid = clientId.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                cid = clientId.getItemAtPosition(0).toString();
            }
        });



        btnsubmit = view.findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ft = fromTime.getText().toString();

                String tt = toTime.getText().toString();


                ContentValues contentValues = new ContentValues();
                contentValues.put(dconstant.RCID,cid);
                contentValues.put(dconstant.RDATE,date2);
                contentValues.put(dconstant.RFT,ft);


                contentValues.put(dconstant.RTT,tt);

                long row = sq.insert(dconstant.TABLE_NAME4,null,contentValues);
                if(row > 0)
                {
                    Toast.makeText(getActivity(), "Request sent to driver successfully!!", Toast.LENGTH_SHORT).show();
                }
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new Home()).commit();



            }
        });









        btncancel = view.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new Home()).commit();
            }
        });

        return view;
    }
    public void populateSpinner()
    {
        Cursor cursor = sq.query(dconstant.TABLE_NAME2,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            String id2 = cursor.getString(cursor.getColumnIndex(dconstant.CID));
            clist.add(id2);


        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,clist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientId.setAdapter(adapter2);
    }
}
