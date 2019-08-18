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

public class Feedbackf extends Fragment
{
    EditText feedid,feedtext ;
    TextView date;
    Spinner spind,spinc;
    DatePickerDialog pickerDialog;
    Button btnsubmit;
    SQLiteDatabase sq;
    dmanager manager;
    ArrayList<String> dlist,clist;
    String did,cid,date2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.feedbackf,null,false);
        manager = new dmanager(getActivity());
        sq = manager.OpenDb();
        Calendar calendar=Calendar.getInstance();
        final int tmonth=calendar.get(Calendar.MONTH);
        final int tyear=calendar.get(Calendar.YEAR);
        final int tday=calendar.get(Calendar.DATE);
        dlist = new ArrayList<>();
        clist = new ArrayList<>();

        feedid= view.findViewById(R.id.feedid);
        feedtext = view.findViewById(R.id.feedtext);
        btnsubmit=view.findViewById(R.id.btnsubmit);
        spind=view.findViewById(R.id.driverId);
        spinc=view.findViewById(R.id.clientId);
        date=view.findViewById(R.id.date);
        populateSpinDriver();
        populateSpinClient();
        spind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                did = spind.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                did = spind.getItemAtPosition(0).toString();
            }
        });

        spinc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cid = spinc.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cid = spinc.getItemAtPosition(0).toString();

            }
        });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog=new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         date2=dayOfMonth+"/"+(month+1)+"/"+year;
                        date.setText(date2);
                        Toast.makeText(getActivity(), "date is"+date2, Toast.LENGTH_SHORT).show();
                    }
                }, tyear, tmonth, tday);
                pickerDialog.show();


            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fid = feedid.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(dconstant.FID,fid);
                contentValues.put(dconstant.FDID,did);

                contentValues.put(dconstant.FCID,cid);
                contentValues.put(dconstant.FDATE,date2);
                String feed =feedtext.getText().toString();
                contentValues.put(dconstant.FTEXT,feed);
                long row = sq.insert(dconstant.TABLE_NAME3,null,contentValues);
                if(row > 0)
                {
                    Toast.makeText(getActivity(), "Feedback submitted successfully!!" , Toast.LENGTH_SHORT).show();
                }
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new Home()).commit();


            }
        });





        return view;
    }
    public void populateSpinDriver()
    {
        Cursor cursor = sq.query(dconstant.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            String id = cursor.getString(cursor.getColumnIndex(dconstant.DID));
            dlist.add(id);


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spind.setAdapter(adapter);






    }
    public void populateSpinClient()
    {
        Cursor cursor2 = sq.query(dconstant.TABLE_NAME2,null,null,null,null,null,null);
        while (cursor2.moveToNext())
        {
            String id2 = cursor2.getString(cursor2.getColumnIndex(dconstant.CID));
            clist.add(id2);


        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,clist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinc.setAdapter(adapter2);
    }


}
