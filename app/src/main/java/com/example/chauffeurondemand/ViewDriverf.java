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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chauffeurondemand.com.beans.Driver;
import com.example.chauffeurondemand.com.dbtask.dconstant;
import com.example.chauffeurondemand.com.dbtask.dmanager;

import java.util.ArrayList;

public class ViewDriverf extends Fragment {
    Spinner spinner;
    dmanager manager;
    SQLiteDatabase sq;
    TextView name, email, phone, age, address;
    ArrayList<String> dlist;
    Driver driver;
    Button btnback;
    String x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_driverf, null, false);

        dlist = new ArrayList<>();
        spinner = view.findViewById(R.id.driverspin);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        age = view.findViewById(R.id.age);
        address = view.findViewById(R.id.address);

        manager = new dmanager(getActivity());
        sq = manager.OpenDb();
        populateList();
        btnback = view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, new DriverDetailsf()).commit();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String did = spinner.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
                fetchDetails(did);
                x = did;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //did = driverspin.getItemAtPosition(0).toString();


            }
        });


        return view;
    }

    public void populateList() {
        Cursor cursor = sq.query(dconstant.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(dconstant.DID));


            dlist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dlist);
        spinner.setAdapter(adapter);
    }

    public void fetchDetails(String did) {

        String[] args = {did};
        //String[]assign={data.tostring(),String.valueOf(fr),}
        // Cursor c1=sq.rawQuery()

        Cursor cursor = sq.query(dconstant.TABLE_NAME, null, dconstant.DID + "=?", args, null, null, null);

        while (cursor.moveToNext()) {
            String nm = cursor.getString(cursor.getColumnIndex(dconstant.DNAME));
            String em = cursor.getString(cursor.getColumnIndex(dconstant.DEMAIL));
            String ph = cursor.getString(cursor.getColumnIndex(dconstant.DPHONE));
            String age1 = cursor.getString(cursor.getColumnIndex(dconstant.DAGE));
            String add = cursor.getString(cursor.getColumnIndex(dconstant.DADDRESS));

            name.setText(nm);
            email.setText(em);
            phone.setText(ph);
            age.setText(age1);
            address.setText(add);


            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }

    }
}

