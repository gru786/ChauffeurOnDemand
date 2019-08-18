package com.example.chauffeurondemand;

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

public class AssignDriverf extends Fragment
{
    Spinner driverspin,ridspin;
    TextView date,clientId,txtft,txttt;
    EditText txtcharge;
    dmanager manager;
    SQLiteDatabase sq;
    ArrayList<String> dlist,dlist1;
    String rid,rdate,rft,rtt,did,rclient;
    Button btnsubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assign_driverf,null,false);
        dlist = new ArrayList<>();
        dlist1 = new ArrayList<>();

        manager = new dmanager(getActivity());
        sq = manager.OpenDb();
        driverspin = view.findViewById(R.id.driverspin);
        clientId = view.findViewById(R.id.clientId);
        ridspin = view.findViewById(R.id.ridspin);
        date = view.findViewById(R.id.date);
        txtft = view.findViewById(R.id.txtft);
        txttt = view.findViewById(R.id.txttt);
        btnsubmit = view.findViewById(R.id.btnsubmit);

        txtcharge = view.findViewById(R.id.txtcharge);
        populateSpinnerRequest();

        ridspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rid = ridspin.getItemAtPosition(position).toString();
                fillClient(rid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        populateSpinnerDriver();
        driverspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                did = driverspin.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(dconstant.ADID,did);

                contentValues.put(dconstant.ACID,rclient);
                contentValues.put(dconstant.ADATE,rdate);
                contentValues.put(dconstant.AFT,rft);
                contentValues.put(dconstant.ATT,rtt);
                String charge = txtcharge.getText().toString();
                float ch = Float.parseFloat(charge);
                contentValues.put(dconstant.ATOTAL,ch);
                long row = sq.insert(dconstant.TABLE_NAME5,null,contentValues);
                if(row > 0)
                {
                    Toast.makeText(getActivity(),"Driver assigned successfully!!" , Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame,new Home()).commit();

                }


            }
        });






        return view;

    }
    public void populateSpinnerRequest()
    {
        Cursor cursor = sq.query(dconstant.TABLE_NAME4,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            String  id = cursor.getString(cursor.getColumnIndex(dconstant.RID));

            dlist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ridspin.setAdapter(adapter);
    }
    public void fillClient(String id)

    {

        String[]args={id};


        Cursor cursor = sq.query(dconstant.TABLE_NAME4,null,dconstant.RID+"=?",args,null,null,null);

        while (cursor.moveToNext()) {
            rclient = cursor.getString(cursor.getColumnIndex(dconstant.RCID));
            rdate = cursor.getString(cursor.getColumnIndex(dconstant.RDATE));
            rft = cursor.getString(cursor.getColumnIndex(dconstant.RFT));
            rtt = cursor.getString(cursor.getColumnIndex(dconstant.RTT));

            clientId.setText(rclient);
            date.setText(rdate);
            txtft.setText(rft);
            txttt.setText(rtt);

            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }




    }
    public void populateSpinnerDriver()
    {
        String[]assign={rdate,rft,rtt};
        Cursor c1=sq.rawQuery("select * from DriverDetail where DriverId not in(select DriverId from AssignDriver where Date =? and ((? >= FromTime and ? <= ToTime) or (? >= FromTime and ? < ToTime) or (FromTime >= ? and ToTime <= ?)or (ToTime >= ? and ToTime <= ?)))",null);
        /*String q= "Select * From DriverDetail where DriverId not in(select DriverId from AssignDriver Where  Date = '"+incoming_date+"' AND (('"+incoming_fromt+"'>= FromTime AND '"+incoming_fromt+"' <= ToTime) OR ('"+incoming_tot+"' >= FromTime AND '"+incoming_tot+"' < ToTime) OR (FromTime >= '"+incoming_fromt+"' AND ToTime <= '"+incoming_fromt+"') OR (ToTime >= '"+incoming_fromt+"' AND ToTime <= '"+incoming_tot+"')))";
        Cursor cursor = sq.rawQuery(q, null);
        while (cursor.moveToNext()) */




        while (c1.moveToNext()) {
            String driverid = c1.getString(c1.getColumnIndex(dconstant.DID));
            dlist1.add(driverid);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dlist1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverspin.setAdapter(adapter);

    }
}
