package com.example.chauffeurondemand;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chauffeurondemand.com.dbtask.dconstant;
import com.example.chauffeurondemand.com.dbtask.dmanager;

import java.util.ArrayList;

public class ViewFeedf extends Fragment
{
    Spinner spinner;
    TextView did,cid,date,ftext;
    Button btncancel;
    dmanager manager;
    SQLiteDatabase sq;
    ArrayList<String> dlist;
    String x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewfeedf,null,false);
        dlist = new ArrayList<>();
        manager = new dmanager(getActivity());
        sq = manager.OpenDb();
        spinner = view.findViewById(R.id.spinner);



        did = view.findViewById(R.id.did);
        cid = view.findViewById(R.id.cid);
        date = view.findViewById(R.id.date);
        ftext = view.findViewById(R.id.ftext);

        btncancel = view.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new Feedf()).commit();
            }
        });
        populateList();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dids = spinner.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
                fetchDetails(dids);
                x= dids;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //did = driverspin.getItemAtPosition(0).toString();


            }
        });

        return view;
    }
    public void populateList()
    {
        Cursor cursor = sq.query(dconstant.TABLE_NAME3,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            String  id = cursor.getString(cursor.getColumnIndex(dconstant.FID));

            dlist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void fetchDetails(String didz)

    {

        String[]args={didz};
        //String[]assign={data.tostring(),String.valueOf(fr),}
        // Cursor c1=sq.rawQuery()

        Cursor cursor = sq.query(dconstant.TABLE_NAME3,null,dconstant.FID+"=?",args,null,null,null);

        while (cursor.moveToNext()) {
            String d_id = cursor.getString(cursor.getColumnIndex(dconstant.FDID));
            String c_id = cursor.getString(cursor.getColumnIndex(dconstant.FCID));
            String da = cursor.getString(cursor.getColumnIndex(dconstant.FDATE));
            String txt = cursor.getString(cursor.getColumnIndex(dconstant.FTEXT));
            did.setText(d_id);
            cid.setText(c_id);
            date.setText(da);
            ftext.setText(txt);

            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }




    }

}
