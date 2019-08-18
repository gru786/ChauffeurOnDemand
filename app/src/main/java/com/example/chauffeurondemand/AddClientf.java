package com.example.chauffeurondemand;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chauffeurondemand.com.dbtask.dconstant;
import com.example.chauffeurondemand.com.dbtask.dmanager;

public class AddClientf extends Fragment
{
    EditText txtcid,txtcname,txtcmail,txtcphno,txtcaddress;
    Button btnsubmit,btnback;
    SQLiteDatabase sq;
    dmanager manager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_clientf,null,false);
        manager = new dmanager(getActivity());
        sq = manager.OpenDb();

        txtcid = view.findViewById(R.id.txtclientid);
        txtcname = view.findViewById(R.id.txtclientname);
        txtcmail = view.findViewById(R.id.txtclientmail);
        txtcphno=view.findViewById(R.id.txtclientphone);

        txtcaddress=view.findViewById(R.id.txtclientaddress);
        btnback = view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new ClientDetailsf()).commit();
            }
        });
        btnsubmit = view.findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id =txtcid.getText().toString().trim();
                String name = txtcname.getText().toString().trim();
                String email = txtcmail.getText().toString().trim();
                String phone = txtcphno.getText().toString().trim();

                String address = txtcaddress.getText().toString().trim();
                ContentValues contentValues = new ContentValues();
                contentValues.put(dconstant.CID,id);

                contentValues.put(dconstant.CNAME,name);
                contentValues.put(dconstant.CEMAIL,email);
                contentValues.put(dconstant.CPHONE,phone);


                contentValues.put(dconstant.CADDRESS,address);
                long row = sq.insert(dconstant.TABLE_NAME2,null,contentValues);
                if(row > 0)
                {
                    Toast.makeText(getActivity(), "Client details added successfully!!" , Toast.LENGTH_SHORT).show();
                }
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new ClientDetailsf()).commit();



            }
        });
        return view;

    }
}
