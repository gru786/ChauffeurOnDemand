package com.example.chauffeurondemand;

import android.app.AlertDialog;
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

public class UpdateClientf extends Fragment
{
    dmanager manager;
    SQLiteDatabase sq;
    ArrayList<String> clist;

    Button btnback,btnupdate;
    Spinner clientspin;
    TextView email,phone;
    EditText newemail,newphone;
    String x;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_clientf,null,false);
        clist = new ArrayList<>();
        clientspin = view.findViewById(R.id.clientspin);
        manager = new dmanager(getActivity());
        sq = manager.OpenDb();
        populateList();

        btnback = view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new ClientDetailsf()).commit();
            }
        });
        clientspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cid = clientspin.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), ""+did, Toast.LENGTH_SHORT).show();
                fetchDetails(cid);
                x= cid;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //did = driverspin.getItemAtPosition(0).toString();


            }
        });
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        newemail = view.findViewById(R.id.newemail);
        newphone = view.findViewById(R.id.newphone);
        btnupdate = view.findViewById(R.id.btnupdate);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails(x);
            }
        });











        return view;
    }

    public void populateList()
    {
        Cursor cursor = sq.query(dconstant.TABLE_NAME2,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(dconstant.CID));

            clist.add(id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,clist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientspin.setAdapter(adapter);
    }
    public void fetchDetails(String cid)

    {
        String[] selcolumns = {dconstant.CEMAIL,dconstant.CPHONE};
        String[]args={cid};


        Cursor cursor = sq.query(dconstant.TABLE_NAME2,selcolumns,dconstant.CID+"=?",args,null,null,null);

        while (cursor.moveToNext()) {
            String email1 = cursor.getString(cursor.getColumnIndex(dconstant.CEMAIL));
            String phone1 = cursor.getString(cursor.getColumnIndex(dconstant.CPHONE));
            email.setText(email1);
            phone.setText(phone1);

            // Toast.makeText(getActivity(), email + phone, Toast.LENGTH_SHORT).show();
        }




    }
    public void saveDetails(String id)
    {
        String em = newemail.getText().toString();
        if(em.isEmpty())
        {
            Toast.makeText(getActivity(), "Please write the same mail,if u don't wish to update!!", Toast.LENGTH_SHORT).show();
        }
        String ph = newphone.getText().toString();
        if(em.isEmpty())
        {
            Toast.makeText(getActivity(), "Please write the same phone no.,if u don't wish to update!!", Toast.LENGTH_SHORT).show();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(dconstant.CEMAIL,em);
        contentValues.put(dconstant.CPHONE,ph);
        String[] args = {id};

        int rw = sq.update(dconstant.TABLE_NAME2,contentValues,dconstant.CID+"=?",args);
        if(rw>0) {
            Toast.makeText(getActivity(), "Client details updated sucessfully", Toast.LENGTH_SHORT).show();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, new ClientDetailsf()).commit();
        }


    }
}
